package br.pucminas.stockmarket.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.dto.StockDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.entities.Stock;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.mappers.StockMapper;
import br.pucminas.stockmarket.api.repositories.StockRepository;
import br.pucminas.stockmarket.api.services.StockService;

@Service
public class StockServiceImpl implements StockService
{
	StockRepository stockRepository;
	StockMapper stockMapper;

	public StockServiceImpl(StockRepository p_stockRepository, StockMapper p_stockMapper) 
	{
		this.stockRepository = p_stockRepository;
		this.stockMapper = p_stockMapper;
	}
	
	@Override
	public List<StockDTO> findAllStocksDTOByCompanyId(Long companyId)
	{
		return StreamSupport.stream(stockRepository.findAllStocksByCompanyId(companyId)
				.spliterator(), false)
				.map(stockMapper::stockToStockDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Stock> findAllStocksEntityByCompanyId(Long companyId)
	{
		return stockRepository.findAllStocksByCompanyId(companyId);
	}

	@Override
	public Optional<Stock> findStockById(Long stockId) 
	{
		return stockRepository.findById(stockId);
	}

	@Override
	public Stock insert(Stock stock) 
	{
		return stockRepository.save(stock);
	}

	@Override
	public Stock update(Stock stock)
	{
		return stockRepository.save(stock);
	}

	@Override
	public Double calculateStockCurrentValue(List<HistoricalStockPrice> historicalPrices, CalculationTypeEnum calcType) 
	{	
		Double minValue = 10.0;
		Double maxValue = 12.0;
		boolean first = true;
		if(historicalPrices!=null && historicalPrices.size() > 0)
		{
			for (HistoricalStockPrice historicalPrice : historicalPrices) 
			{
				if(first)
				{
					minValue =	historicalPrice.getPrice();
					maxValue = historicalPrice.getPrice();
					first = false;
				}
				if(historicalPrice.getPrice() <= minValue)
				{
					minValue =	historicalPrice.getPrice();					
				}
				if(historicalPrice.getPrice() >= maxValue)
				{
					maxValue = historicalPrice.getPrice();
				}
			}
		}
		
		switch (calcType)
		{
	    	case BUY:
	    		maxValue += (maxValue * 0.10);
	    		break;
	    	case SELL:
	    		maxValue += (maxValue * -0.02) * -1;
	    		break;
	    	case CREATE:
	    		maxValue += (maxValue * -0.05) * -1;
	    		break;
	    	case UPDATE:
	    		maxValue += (maxValue * -0.02) * -1;
	    		break;
	    	case CURRENT_VALUE:
	    		Double valor = generateRandomNumber(1.0, 2.0);
	    		if(valor == 1.0)
	    		{
	    			maxValue += (maxValue * -0.03) * -1;
		    		break;
	    		}
	    		else
	    		{
	    			maxValue += (maxValue * 0.03) * 1;
		    		break;
	    		}
		}

		Double actualValue = generateRandomNumber(minValue, maxValue);
		
		return actualValue;
	}
	
	private Double generateRandomNumber(Double min, Double max)
	{
		Double randomNum = min + (Double)(Math.random() * (max - min));

	    return randomNum;
	}
}