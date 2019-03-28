package br.pucminas.stockmarket.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.dto.CompanyDTO;
import br.pucminas.stockmarket.api.entities.Company;
import br.pucminas.stockmarket.api.mappers.CompanyMapper;
import br.pucminas.stockmarket.api.repositories.CompanyRepository;
import br.pucminas.stockmarket.api.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService
{
	CompanyRepository companyRepository;
	CompanyMapper companyMapper;
	
	public CompanyServiceImpl(CompanyRepository p_companyRepository, CompanyMapper p_companyMapper) 
	{
		this.companyRepository = p_companyRepository;
		this.companyMapper = p_companyMapper;
	}
	
	@Override
	public List<CompanyDTO> findAllCompaniesDTO() 
	{
    	return StreamSupport.stream(this.companyRepository.findAll()
			.spliterator(), false)
			.map(companyMapper::companyToCompanyDTO)
			.collect(Collectors.toList());
	}
	
	@Override
	public List<Company> findAllCompaniesEntity() 
	{
    	return this.companyRepository.findAll();
	}

	@Override
	public Optional<Company> findCompanyById(Long companyId) 
	{
		Optional<Company> company = companyRepository.findById(companyId);
		
		return company;
	}
	
	@Override
	public Company insert(Company company)
	{
		company = companyRepository.save(company);
		
		return company;
	}

	@Override
	
	public Company update(Company company) 
	{
		company = companyRepository.save(company);
		
		return company;
	}



}
