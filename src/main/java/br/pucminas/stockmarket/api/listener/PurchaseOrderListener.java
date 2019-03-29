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
import br.pucminas.stockmarket.api.entities.InvestmentWallet;
import br.pucminas.stockmarket.api.entities.PurchaseOrder;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.mappers.PurchaseOrderMapper;
import br.pucminas.stockmarket.api.services.HistoricalStockPriceService;
import br.pucminas.stockmarket.api.services.InvestmentService;
import br.pucminas.stockmarket.api.services.InvestmentWalletService;
import br.pucminas.stockmarket.api.services.PurchaseOrderService;
import br.pucminas.stockmarket.api.services.StockService;

@Component
public class PurchaseOrderListener 
{	
	static final Logger logger = LoggerFactory.getLogger(PurchaseOrderListener.class);

	PurchaseOrderService purchaseOrderService;
	InvestmentService investmentService;
	InvestmentWalletService investmentWalletService;
	StockService stockService;
	HistoricalStockPriceService historicalStockPriceService;
	PurchaseOrderMapper purchaseOrderMapper;

	
	public PurchaseOrderListener(PurchaseOrderService p_purchaseOrderService, InvestmentService p_investmentService, 
								InvestmentWalletService p_investmentWalletService, StockService p_stockService, 
								HistoricalStockPriceService p_historicalStockPriceService, PurchaseOrderMapper p_purchaseOrderMapper) 
	{
		this.purchaseOrderService = p_purchaseOrderService;
		this.investmentService = p_investmentService;
		this.stockService = p_stockService;
		this.historicalStockPriceService = p_historicalStockPriceService;
		this.investmentWalletService = p_investmentWalletService;

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
		Double stockCurrentValue = stockService.calculateStockCurrentValue(purchaseOrder.getStock().getHistoricalStockPrices(), CalculationTypeEnum.BUY);
		if(!investmentWalletOptional.isPresent())
        {
			investmentWallet = new InvestmentWallet();
			investmentWallet.setInvestor(purchaseOrder.getInvestor());
			investmentWallet.setCreationDate(currentDate);
			investmentWallet.setLastUpdate(currentDate);
			
			investmentWalletService.insert(investmentWallet);
        }
		
       	Investment investment = new Investment();
    	investment.setCreationDate(currentDate);
    	investment.setInvestmentWallet(investmentWallet);
    	investment.setStock(purchaseOrder.getStock());
    	investment.setStockQuantity(purchaseOrder.getAmount());
    	investment.setPurchaseStockValue(stockCurrentValue);
    	investment.setLastUpdate(currentDate);
    	
    	investmentService.insert(investment);

    	HistoricalStockPrice HistoricalStockPrice = new HistoricalStockPrice();
    	HistoricalStockPrice.setStock(purchaseOrder.getStock());
    	HistoricalStockPrice.setPrice(stockCurrentValue);
    	HistoricalStockPrice.setReferenceDate(currentDate);
    	
    	historicalStockPriceService.insert(HistoricalStockPrice);
	}
}
