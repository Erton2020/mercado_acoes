package br.pucminas.stockmarket.api.services;

import java.util.List;

import br.pucminas.stockmarket.api.dto.HistoricalStockPriceDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;

public interface HistoricalStockPriceService {

	List<HistoricalStockPriceDTO> findAllHistoricalStockPriceDTOByStockId(Long stockId);
	
	HistoricalStockPrice insert(HistoricalStockPrice historicalStockPrice);
}
