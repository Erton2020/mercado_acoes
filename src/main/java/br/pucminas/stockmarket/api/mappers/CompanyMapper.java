package br.pucminas.stockmarket.api.mappers;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.CompanyDTO;
import br.pucminas.stockmarket.api.entities.Company;

@Component
public class CompanyMapper 
{
	
	AddressMapper addressMapper;
	
	public CompanyMapper(AddressMapper p_addressMapper) 
	{
		this.addressMapper = p_addressMapper;
	}

	public CompanyDTO companyToCompanyDTO(Company company) 
	{
		final CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		companyDTO.setCreationDate(company.getCreationDate());
		companyDTO.setAddress(addressMapper.addressToAddressDTO(company.getAddress()));	
		
		/*		if (garage.getCategories() != null && garage.getCategories().size() > 0){
		garage.getCategories()
                .forEach(category -> garageDTO.getCategories().add(categoryMapper.categoryToCategoryDTO(category)));*/
			
		return companyDTO;
	}

	public Company companyDTOTOCompany(CompanyDTO companyDTO) 
	{
		final Company company = new Company();
		company.setId(companyDTO.getId());
		company.setName(companyDTO.getName());
		if(companyDTO.getCreationDate() == null)
		{
			company.setCreationDate(Calendar.getInstance());
		}
		company.setLastUpdate(Calendar.getInstance());
		
		return company;
	}

	public Company updateCompany(Company company, CompanyDTO companyDTO)
	{
		company.setName(companyDTO.getName());
		company.setLastUpdate(Calendar.getInstance());
		
		return company;
	}

}
