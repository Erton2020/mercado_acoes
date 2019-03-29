package br.pucminas.stockmarket.api.mappers;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.PurchaseOrderDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.entities.PurchaseOrder;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.services.InvestorService;
import br.pucminas.stockmarket.api.services.StockService;

@Component
public class PurchaseOrderMapper {
	
	InvestorService investorService;
	StockService stockService;
	
	public PurchaseOrderMapper(InvestorService p_investorService,StockService p_stockService) 
	{
		this.investorService = p_investorService;
		this.stockService = p_stockService;
	}
	
	public PurchaseOrder purchaseOrderDTOTOPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) 
	{
		Optional<Investor> investorOptional = investorService.findInvestorById(purchaseOrderDTO.getInvestorId());
		Optional<Stock> stockOptional = stockService.findStockById(purchaseOrderDTO.getId());	

		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setInvestor(investorOptional.get());
		purchaseOrder.setStock(stockOptional.get());
		purchaseOrder.setAmount(purchaseOrderDTO.getAmount());
		purchaseOrder.setPurchaseDate(Calendar.getInstance());
		
		Double currentStockValue = stockService.calculateStockCurrentValue(stockOptional.get().getHistoricalStockPrices(), CalculationTypeEnum.BUY);
		
		purchaseOrder.setUnitPurchasePrice(currentStockValue);

        return purchaseOrder;
		
	}

}
 