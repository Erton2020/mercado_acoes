package br.pucminas.stockmarket.api.listener;

import java.util.Calendar;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.config.RabbitMQConfig;
import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.entities.Investment;
import br.pucminas.stockmarket.api.entities.InvestmentHistorical;
import br.pucminas.stockmarket.api.entities.InvestmentWallet;
import br.pucminas.stockmarket.api.entities.SaleOrder;
import br.pucminas.stockmarket.api.enums.InvestmentHistoricalTypeEnum;
import br.pucminas.stockmarket.api.mappers.SaleOrderMapper;
import br.pucminas.stockmarket.api.services.HistoricalStockPriceService;
import br.pucminas.stockmarket.api.services.InvestmentHistoricalService;
import br.pucminas.stockmarket.api.services.InvestmentService;
import br.pucminas.stockmarket.api.services.InvestmentWalletService;
import br.pucminas.stockmarket.api.services.SaleOrderService;
import br.pucminas.stockmarket.api.services.StockService;
import br.pucminas.stockmarket.api.utils.EmailSenderUtil;
import br.pucminas.stockmarket.api.utils.EmailTemplateUtil;

@Component
public class SaleOrderListener 
{
	static final Logger logger = LoggerFactory.getLogger(SaleOrderListener.class);

	SaleOrderService saleOrderService;
	InvestmentService investmentService;
	InvestmentWalletService investmentWalletService;
	StockService stockService;
	HistoricalStockPriceService historicalStockPriceService;
	InvestmentHistoricalService investmentHistoricalService;
	SaleOrderMapper saleOrderMapper;
	EmailTemplateUtil emailTemplateUtil;
	EmailSenderUtil emailSenderUtil;
	
	
    public SaleOrderListener(SaleOrderService p_saleOrderService, InvestmentService p_investmentService,
			InvestmentWalletService p_investmentWalletService, StockService p_stockService,
			HistoricalStockPriceService p_historicalStockPriceService,
			InvestmentHistoricalService p_investmentHistoricalService, SaleOrderMapper p_saleOrderMapper,
			EmailTemplateUtil p_emailTemplateUtil, EmailSenderUtil p_emailSenderUtil) 
    {
		this.saleOrderService = p_saleOrderService;
		this.investmentService = p_investmentService;
		this.investmentWalletService = p_investmentWalletService;
		this.stockService = p_stockService;
		this.historicalStockPriceService = p_historicalStockPriceService;
		this.investmentHistoricalService = p_investmentHistoricalService;
		this.saleOrderMapper = p_saleOrderMapper;
		this.emailTemplateUtil = p_emailTemplateUtil;
		this.emailSenderUtil = p_emailSenderUtil;
	}


	@RabbitListener(queues = RabbitMQConfig.QUEUE_SALE_ORDER)
    public void processSaleOrder(SaleOrderDTO saleOrderDTO)
    {
        logger.info("Processando ordem de compra!");
        SaleOrder saleOrder = saleOrderMapper.saleOrderDTOTOSaleOrder(saleOrderDTO); 
        saleOrder = saleOrderService.insert(saleOrder);
        
        Optional<InvestmentWallet> investmentWalletOptional = investmentWalletService.findInvestmentWalletByInvestorId(saleOrderDTO.getInvestorId());
        
        createInvestmentRegister(investmentWalletOptional, saleOrder);
    }


	private void createInvestmentRegister(Optional<InvestmentWallet> investmentWalletOptional, SaleOrder saleOrder)
	{
		InvestmentWallet investmentWallet = null;
		Calendar currentDate = Calendar.getInstance();
		Double stockCurrentValue = saleOrder.getUnitSalePrice();
		if(!investmentWalletOptional.isPresent())
        {
			investmentWallet = new InvestmentWallet();
			investmentWallet.setInvestor(saleOrder.getInvestor());
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
    	investment.setStock(saleOrder.getStock());
      	investment.setLastUpdate(currentDate);
    	
    	investmentService.insert(investment);
    	
    	InvestmentHistorical  investmentHistorical = new InvestmentHistorical();
    	investmentHistorical.setInvestment(investment);
    	investmentHistorical.setInvestmentHistoricalType(InvestmentHistoricalTypeEnum.SALE);
    	investmentHistorical.setQuantity(saleOrder.getAmount());
    	investmentHistorical.setUnitStockValue(stockCurrentValue);
    	investmentHistorical.setCreationDate(currentDate);
    	
    	investmentHistoricalService.insert(investmentHistorical);

    	HistoricalStockPrice HistoricalStockPrice = new HistoricalStockPrice();
    	HistoricalStockPrice.setStock(saleOrder.getStock());
    	HistoricalStockPrice.setPrice(stockCurrentValue);
    	HistoricalStockPrice.setReferenceDate(currentDate);
    	
    	historicalStockPriceService.insert(HistoricalStockPrice);
    	
    	sendEmailSaleOrderConfirmation(saleOrder);
	}
	
	
	private void sendEmailSaleOrderConfirmation(SaleOrder saleOrder) 
	{	
		String bodyInvestorEmail = emailTemplateUtil.saleOrderConfirmationInvestorEmailBody(saleOrder.getInvestor().getName(), saleOrder.getStock().getDescription(), 
				saleOrder.getStock().getStockType().toString(), saleOrder.getStock().getCompany().getName(), saleOrder.getAmount(), saleOrder.getUnitSalePrice());			
		String subjectInvestorEmail = "Confirmação de venda concluida de ações da empresa " + saleOrder.getStock().getCompany().getName();
		emailSenderUtil.sendEmail(saleOrder.getInvestor().getEmail(),subjectInvestorEmail, bodyInvestorEmail);
		
		String bodyComapanyEmail = emailTemplateUtil.saleOrderConfirmationCompanyEmailBody(saleOrder.getInvestor().getName(), 
				saleOrder.getStock().getDescription(), saleOrder.getStock().getStockType().toString(), saleOrder.getStock().getCompany().getName(), 
				saleOrder.getAmount(), saleOrder.getUnitSalePrice());		
		
		String subjectCompanyEmail = "Confirmação de recompra concluida de "+ saleOrder.getAmount() + " ações " + saleOrder.getStock().getDescription();
		
		emailSenderUtil.sendEmail(saleOrder.getStock().getCompany().getEmail(),subjectCompanyEmail, bodyComapanyEmail);	
	}
}
