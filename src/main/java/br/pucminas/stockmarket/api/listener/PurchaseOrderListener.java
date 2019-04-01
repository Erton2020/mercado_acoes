package br.pucminas.stockmarket.api.listener;

import java.util.Calendar;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.entities.Investment;
import br.pucminas.stockmarket.api.entities.InvestmentHistorical;
import br.pucminas.stockmarket.api.entities.InvestmentWallet;
import br.pucminas.stockmarket.api.entities.PurchaseOrder;
import br.pucminas.stockmarket.api.enums.InvestmentHistoricalTypeEnum;
import br.pucminas.stockmarket.api.mappers.PurchaseOrderMapper;
import br.pucminas.stockmarket.api.services.HistoricalStockPriceService;
import br.pucminas.stockmarket.api.services.InvestmentHistoricalService;
import br.pucminas.stockmarket.api.services.InvestmentService;
import br.pucminas.stockmarket.api.services.InvestmentWalletService;
import br.pucminas.stockmarket.api.services.PurchaseOrderService;
import br.pucminas.stockmarket.api.services.StockService;
import br.pucminas.stockmarket.api.utils.EmailSenderUtil;
import br.pucminas.stockmarket.api.utils.EmailTemplateUtil;

@Component
public class PurchaseOrderListener 
{	
	static final Logger logger = LoggerFactory.getLogger(PurchaseOrderListener.class);

	PurchaseOrderService purchaseOrderService;
	InvestmentService investmentService;
	InvestmentWalletService investmentWalletService;
	StockService stockService;
	HistoricalStockPriceService historicalStockPriceService;
	InvestmentHistoricalService investmentHistoricalService;
	PurchaseOrderMapper purchaseOrderMapper;
	EmailTemplateUtil emailTemplateUtil;
	EmailSenderUtil emailSenderUtil;
	
	public PurchaseOrderListener(PurchaseOrderService p_purchaseOrderService, InvestmentService p_investmentService, 
								InvestmentWalletService p_investmentWalletService, StockService p_stockService, 
								HistoricalStockPriceService p_historicalStockPriceService, InvestmentHistoricalService p_investmentHistoricalService,
								PurchaseOrderMapper p_purchaseOrderMapper, EmailTemplateUtil p_emailTemplateUtil, EmailSenderUtil p_emailSenderUtil) 
	{
		this.purchaseOrderService = p_purchaseOrderService;
		this.investmentService = p_investmentService;
		this.stockService = p_stockService;
		this.historicalStockPriceService = p_historicalStockPriceService;
		this.investmentHistoricalService = p_investmentHistoricalService;
		this.investmentWalletService = p_investmentWalletService;
		this.emailTemplateUtil = p_emailTemplateUtil;
		this.emailSenderUtil =p_emailSenderUtil;

	}
	
    @RabbitListener(queues = RabbitMQConfig.QUEUE_PURCHASE_ORDER)
    public void processPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO)
    {
        logger.info("Processando ordem de compra!");

        PurchaseOrder purchaseOrder = purchaseOrderMapper.purchaseOrderDTOTOPurchaseOrder(purchaseOrderDTO); 
        purchaseOrder = purchaseOrderService.insert(purchaseOrder);
        
        Optional<InvestmentWallet> investmentWalletOptional = investmentWalletService.findInvestmentWalletByInvestorId(purchaseOrderDTO.getInvestorId());
        
        createInvestmentRegister(investmentWalletOptional, purchaseOrder);
    }


	private void createInvestmentRegister(Optional<InvestmentWallet> investmentWalletOptional, PurchaseOrder purchaseOrder)
	{
		InvestmentWallet investmentWallet = null;
		Calendar currentDate = Calendar.getInstance();
		Double stockCurrentValue = purchaseOrder.getUnitPurchasePrice();
		if(!investmentWalletOptional.isPresent())
        {
			investmentWallet = new InvestmentWallet();
			investmentWallet.setInvestor(purchaseOrder.getInvestor());
			investmentWallet.setCreationDate(currentDate);
			investmentWallet.setLastUpdate(currentDate);
			
			investmentWallet = investmentWalletService.insert(investmentWallet);
        }
		else
		{
			investmentWallet = investmentWalletOptional.get();
		}
		
       	Investment investment = new Investment();
    	investment.setCreationDate(currentDate);
    	investment.setInvestmentWallet(investmentWallet);
    	investment.setStock(purchaseOrder.getStock());
      	investment.setLastUpdate(currentDate);
    	
    	investmentService.insert(investment);
    	
    	InvestmentHistorical  investmentHistorical = new InvestmentHistorical();
    	investmentHistorical.setInvestment(investment);
    	investmentHistorical.setInvestmentHistoricalType(InvestmentHistoricalTypeEnum.PURCHASE);
    	investmentHistorical.setQuantity(purchaseOrder.getAmount());
    	investmentHistorical.setUnitStockValue(stockCurrentValue);
    	investmentHistorical.setCreationDate(currentDate);
    	
    	investmentHistoricalService.insert(investmentHistorical);

    	HistoricalStockPrice HistoricalStockPrice = new HistoricalStockPrice();
    	HistoricalStockPrice.setStock(purchaseOrder.getStock());
    	HistoricalStockPrice.setPrice(stockCurrentValue);
    	HistoricalStockPrice.setReferenceDate(currentDate);
    	
    	historicalStockPriceService.insert(HistoricalStockPrice);
    	
    	sendEmailPuchaseOrderConfirmation(purchaseOrder);
	}
	
	
	private void sendEmailPuchaseOrderConfirmation(PurchaseOrder purchaseOrder) 
	{	
		String bodyInvestorEmail = emailTemplateUtil.purchaseOrderConfirmationInvestorEmailBody(purchaseOrder.getInvestor().getName(), purchaseOrder.getStock().getDescription(), 
									purchaseOrder.getStock().getStockType().toString(), purchaseOrder.getStock().getCompany().getName(), purchaseOrder.getAmount(), purchaseOrder.getUnitPurchasePrice());			
		String subjectInvestorEmail = "Confirmação de compra concluida de ações da empresa " + purchaseOrder.getStock().getCompany().getName();
		emailSenderUtil.sendEmail(purchaseOrder.getInvestor().getEmail(),subjectInvestorEmail, bodyInvestorEmail);
		
		String bodyComapanyEmail = emailTemplateUtil.purchaseOrderConfirmationCompanyEmailBody(purchaseOrder.getInvestor().getName(), 
				purchaseOrder.getStock().getDescription(), purchaseOrder.getStock().getStockType().toString(), purchaseOrder.getStock().getCompany().getName(), 
				purchaseOrder.getAmount(), purchaseOrder.getUnitPurchasePrice());		
		
		String subjectCompanyEmail = "Confirmação de venda concluida de "+ purchaseOrder.getAmount() + " ações " + purchaseOrder.getStock().getDescription();
		
		emailSenderUtil.sendEmail(purchaseOrder.getStock().getCompany().getEmail(),subjectCompanyEmail, bodyComapanyEmail);	
	}
	
}
