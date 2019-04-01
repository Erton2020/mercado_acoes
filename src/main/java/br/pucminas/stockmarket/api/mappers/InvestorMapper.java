package br.pucminas.stockmarket.api.mappers;

import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.InvestorDTO;
import br.pucminas.stockmarket.api.entities.Investor;
import br.pucminas.stockmarket.api.enums.InvestorTypeEnum;

@Component
public class InvestorMapper {
	
	
	AddressMapper addressMapper;
	
	public InvestorMapper(AddressMapper p_addressMapper) {
		this.addressMapper = p_addressMapper;
	}
	
	public InvestorDTO investorTOInvestorDTO(Investor investor) 
	{
		InvestorDTO investorDTO = new InvestorDTO();
		investorDTO.setId(investor.getId());
		investorDTO.setName(investor.getName());
		investorDTO.setInvestorType(investor.getInvestorType().toString());
		investorDTO.setAddresses(StreamSupport.stream(investor.getAddresses()
				.spliterator(), false)
				.map(addressMapper::addressToAddressDTO)
				.collect(Collectors.toList()));	
		investorDTO.setCreationDate(investor.getCreationDate());
		
		
		return investorDTO;
	}
	
	public Investor investorDTOTOInvestor(InvestorDTO investorDTO) 
	{
		Investor investor = new Investor();
		investor.setName(investorDTO.getName());
		investor.setInvestorType(InvestorTypeEnum.valueOf(investorDTO.getInvestorType()));
		investor.setAddresses(StreamSupport.stream(investorDTO.getAddresses()
				.spliterator(), false)
				.map(addressMapper::addressDTOToAddress)
				.collect(Collectors.toList()));	
		investor.setCreationDate(investorDTO.getCreationDate());
		
		return investor;
	}

	public Investor updateInvestor(Investor investor, InvestorDTO investorDTO) 
	{
		investor.setName(investorDTO.getName());
		investor.setEmail(investorDTO.getEmail());
		investor.setLastUpdate(Calendar.getInstance());
		
		return investor;
	}

}
