package br.pucminas.stockmarket.api.services;

import java.util.List;
import java.util.Optional;

import br.pucminas.stockmarket.api.dto.StockDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;

public interface StockService 
{
	List<StockDTO> findAllStocksDTOByCompanyId(Long companyId);
	
	List<Stock> findAllStocksEntityByCompanyId(Long companyId);
	
	Optional<Stock> findStockById(Long stockId);

	Stock insert(Stock stock);

	Stock update(Stock stock);
	
	Double calculateStockCurrentValue(List<HistoricalStockPrice> historicalPrices, CalculationTypeEnum calcType);
}
