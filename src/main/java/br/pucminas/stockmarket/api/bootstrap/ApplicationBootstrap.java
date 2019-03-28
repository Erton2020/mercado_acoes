package br.pucminas.stockmarket.api.bootstrap;

import java.util.Calendar;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.entities.Address;
import br.pucminas.stockmarket.api.entities.Company;
import br.pucminas.stockmarket.api.repositories.AddressRepository;
import br.pucminas.stockmarket.api.repositories.CompanyRepository;

@Component
@Profile({"dev"})
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	CompanyRepository companyRepository;
	AddressRepository addressRepository;
	
	public ApplicationBootstrap(CompanyRepository p_companyRepository, AddressRepository p_addressRepository) {
		this.companyRepository = p_companyRepository;
		this.addressRepository = p_addressRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(companyRepository.count() == 0l) {
			insertCompanies();
		}
		
	}

	private void insertCompanies() {
		Address address = new Address(null, " Rua Cláudio Manoel 1161", "Funcionários", "Belo Horizonte", "Minas Gerais",
				"Brasil", Calendar.getInstance(),Calendar.getInstance());
		address = addressRepository.save(address);
		
		Company company = new Company();
		company.setName("Empresa 1");
		company.setAddress(address);
		company.setCreationDate(Calendar.getInstance());
		company.setLastUpdate(Calendar.getInstance());
		
		companyRepository.save(company);	
	}

}
