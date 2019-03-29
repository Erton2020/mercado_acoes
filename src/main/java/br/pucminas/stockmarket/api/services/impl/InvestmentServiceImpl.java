package br.pucminas.stockmarket.api.services.impl;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.entities.Investment;
import br.pucminas.stockmarket.api.repositories.InvestmentRepository;
import br.pucminas.stockmarket.api.services.InvestmentService;

@Service
public class InvestmentServiceImpl implements InvestmentService {

	InvestmentRepository investmentRepository;
	
	public InvestmentServiceImpl(InvestmentRepository p_investmentRepository) {
		investmentRepository = p_investmentRepository;
	}

	@Override
	public Investment insert(Investment investment) 
	{
		return investmentRepository.save(investment);
	}
}
