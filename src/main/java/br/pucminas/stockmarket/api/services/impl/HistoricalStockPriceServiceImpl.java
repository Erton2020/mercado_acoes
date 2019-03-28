package br.pucminas.stockmarket.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.dto.HistoricalStockPriceDTO;
import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;
import br.pucminas.stockmarket.api.mappers.HistoricalStockPriceMapper;
import br.pucminas.stockmarket.api.repositories.HistoricalStockPriceRepository;
import br.pucminas.stockmarket.api.services.HistoricalStockPriceService;

@Service
public class HistoricalStockPriceServiceImpl implements HistoricalStockPriceService{

	HistoricalStockPriceRepository historicalStockPriceRepository; 
	HistoricalStockPriceMapper historicalStockPriceMapper;
	
	public HistoricalStockPriceServiceImpl(HistoricalStockPriceRepository p_historicalStockPriceRepository,
			HistoricalStockPriceMapper p_historicalStockPriceMapper) 
	{
		this.historicalStockPriceRepository = p_historicalStockPriceRepository;
		this.historicalStockPriceMapper = p_historicalStockPriceMapper;
	}
	
	@Override
	public List<HistoricalStockPriceDTO> findAllHistoricalStockPriceDTOByStockId(Long stockId) {

    	return StreamSupport.stream(this.historicalStockPriceRepository.findAllHistoricalStockPriceByStockId(stockId)
			.spliterator(), false)
			.map(historicalStockPriceMapper::hitoricalStockPriceTOHistoricalStockPriceDTO)
			.collect(Collectors.toList());
	}

	@Override
	public HistoricalStockPrice insert(HistoricalStockPrice historicalStockPrice) {
		return historicalStockPriceRepository.save(historicalStockPrice);
	}

}
