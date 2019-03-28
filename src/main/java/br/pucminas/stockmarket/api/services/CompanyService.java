package br.pucminas.stockmarket.api.services;

import java.util.List;
import java.util.Optional;

import br.pucminas.stockmarket.api.dto.CompanyDTO;
import br.pucminas.stockmarket.api.entities.Company;

public interface CompanyService {

	List<CompanyDTO> findAllCompaniesDTO();
	
	List<Company> findAllCompaniesEntity();
	
	Optional<Company> findCompanyById(Long companyId);

	Company insert(Company company);

	Company update(Company company);


}
