package br.pucminas.stockmarket.api.mappers;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.HistoricalStockPriceDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;

@Component
public class HistoricalStockPriceMapper {
	
	public HistoricalStockPriceDTO hitoricalStockPriceTOHistoricalStockPriceDTO(HistoricalStockPrice historicalStockPrice)
	{
		HistoricalStockPriceDTO historicalStockPriceDTO = new HistoricalStockPriceDTO();
		historicalStockPriceDTO.setId(historicalStockPrice.getId());
		historicalStockPriceDTO.setPrice(historicalStockPrice.getPrice());
		historicalStockPriceDTO.setReferenceDate(historicalStockPrice.getReferenceDate());
		
		return historicalStockPriceDTO;
	}
	
	public HistoricalStockPrice hitoricalStockPriceDTOTOHistoricalStockPrice(HistoricalStockPriceDTO historicalStockPriceDTO){

		HistoricalStockPrice historicalStockPrice = new HistoricalStockPrice();
		historicalStockPrice.setPrice(historicalStockPriceDTO.getPrice());
		historicalStockPrice.setReferenceDate(historicalStockPriceDTO.getReferenceDate());
		
		return historicalStockPrice;
	}

}
