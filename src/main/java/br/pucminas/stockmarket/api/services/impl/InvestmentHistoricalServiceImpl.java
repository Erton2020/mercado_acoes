package br.pucminas.stockmarket.api.services.impl;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.entities.InvestmentHistorical;
import br.pucminas.stockmarket.api.repositories.InvestmentHistoricalRepository;
import br.pucminas.stockmarket.api.services.InvestmentHistoricalService;

@Service
public class InvestmentHistoricalServiceImpl implements InvestmentHistoricalService
{
	InvestmentHistoricalRepository investmentHistoricalRepository;
	
	public InvestmentHistoricalServiceImpl(InvestmentHistoricalRepository p_investmentHistoricalRepository) 
	{
		this.investmentHistoricalRepository = p_investmentHistoricalRepository;
	}

	@Override
	public InvestmentHistorical insert(InvestmentHistorical investmentHistorical)
	{
		return investmentHistoricalRepository.save(investmentHistorical);
	}

}
