package br.pucminas.stockmarket.api.mappers;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.StockDTO;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.StockTypeEnum;

@Component
public class StockMapper 
{
	HistoricalStockPriceMapper historicalStockPriceMapper;
	
	public StockMapper(HistoricalStockPriceMapper p_historicalStockPriceMapper)
	{
		this.historicalStockPriceMapper = p_historicalStockPriceMapper;
	}

	public Stock stockDTOTOStock(StockDTO stockDTO) {

		Stock stock = new Stock();
		stock.setId(stockDTO.getId());
		stock.setDescription(stockDTO.getDescription());
		stock.setStockType(StockTypeEnum.valueOf(stockDTO.getStockType()));
		stock.setStocksForSale(stockDTO.getStocksForSale());
		if(stockDTO.getCreationDate() == null)
		{
			stock.setCreationDate(Calendar.getInstance());
		}
		stock.setLastUpdate(Calendar.getInstance());
				
		return stock;
	}

	public StockDTO stockToStockDTO(Stock stock)
	{
		StockDTO stockDTO = new StockDTO();
		stockDTO.setId(stock.getId());
		stockDTO.setCompanyId(stock.getCompany().getId());
		stockDTO.setCompanyName(stock.getCompany().getName());
		stockDTO.setDescription(stock.getDescription());
		stockDTO.setStockType(stock.getStockType().toString());
		stockDTO.setStocksForSale(stock.getStocksForSale());
		stockDTO.setCreationDate(stock.getCreationDate());
		//private List<HistoricalStockPrice> historicalStockPrices;*/
		return stockDTO;
	}
	
	public Stock updateStockWithStockDTO(Stock stock, StockDTO stockDTO)
	{
		stock.setDescription(stockDTO.getDescription());
		stock.setStockType(StockTypeEnum.valueOf(stockDTO.getStockType()));
		stock.setStocksForSale(stockDTO.getStocksForSale());
		stock.setLastUpdate(Calendar.getInstance());
		
		return stock;
	}

}
