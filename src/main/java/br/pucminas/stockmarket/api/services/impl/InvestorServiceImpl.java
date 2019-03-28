package br.pucminas.stockmarket.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.dto.InvestorDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.mappers.InvestorMapper;
import br.pucminas.stockmarket.api.repositories.InvestorRepository;
import br.pucminas.stockmarket.api.services.InvestorService;

@Service
public class InvestorServiceImpl implements InvestorService
{
	InvestorRepository investorRepository;
	InvestorMapper investorMapper;
	
	public InvestorServiceImpl(InvestorRepository p_investorRepository, InvestorMapper p_investorMapper) 
	{
		this.investorRepository = p_investorRepository;
		this.investorMapper = p_investorMapper;
	}

	@Override
	public List<InvestorDTO> findAllInvestorsDTO() 
	{
		return StreamSupport.stream(this.investorRepository.findAll()
				.spliterator(), false)
				.map(investorMapper::investorTOInvestorDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Investor> findInvestorById(Long investorId)
	{
		return investorRepository.findById(investorId);
	}

	@Override
	public Investor insert(Investor investor) 
	{
		return investorRepository.save(investor);
	}

	@Override
	public Investor update(Investor investor) 
	{
		return investorRepository.save(investor);
	}

}
