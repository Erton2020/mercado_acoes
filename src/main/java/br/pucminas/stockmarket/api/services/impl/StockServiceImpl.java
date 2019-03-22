package br.pucminas.stockmarket.api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.dto.StockDTO;
import br.pucminas.stockmarket.api.repositories.StockRepository;
import br.pucminas.stockmarket.api.services.StockService;

@Service
public class StockServiceImpl implements StockService
{
	StockRepository stockRepository;

	public StockServiceImpl(StockRepository p_stockRepository) 
	{
		this.stockRepository = p_stockRepository;
	}
	
	@Override
	public List<StockDTO> findStocksByCompanyId(Long companyId) {
		return stockRepository.findStocksByCompanyId(companyId);
	}

}