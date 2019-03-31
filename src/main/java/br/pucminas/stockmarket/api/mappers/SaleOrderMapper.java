package br.pucminas.stockmarket.api.mappers;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.SaleOrderDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.entities.SaleOrder;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.services.InvestorService;
import br.pucminas.stockmarket.api.services.StockService;

@Component
public class SaleOrderMapper
{
	InvestorService investorService;
	StockService stockService;
	
	public SaleOrderMapper(InvestorService p_investorService,StockService p_stockService) 
	{
		this.investorService = p_investorService;
		this.stockService = p_stockService;
	}

	public SaleOrder saleOrderDTOTOSaleOrder(SaleOrderDTO saleOrderDTO) 
	{
		Optional<Investor> investorOptional = investorService.findInvestorById(saleOrderDTO.getInvestorId());
		Optional<Stock> stockOptional = stockService.findStockById(saleOrderDTO.getStockId());	

		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setInvestor(investorOptional.get());
		saleOrder.setStock(stockOptional.get());
		saleOrder.setAmount(saleOrderDTO.getAmount());
		saleOrder.setSaleDate(Calendar.getInstance());
		
		Double currentStockValue = stockService.calculateStockCurrentValue(stockOptional.get().getHistoricalStockPrices(), CalculationTypeEnum.SALE);
		
		saleOrder.setUnitSalePrice(currentStockValue);
		
		return saleOrder;
	}

}
