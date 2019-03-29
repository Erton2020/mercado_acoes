package br.pucminas.stockmarket.api.mappers;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.AddressDTO;
import br.pucminas.stockmarket.api.entities.Address;

@Component
public class AddressMapper 
{
	public AddressDTO addressToAddressDTO(Address address)
	{
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(address.getId());
		addressDTO.setStreet(address.getStreet());
		addressDTO.setDistrict(address.getDistrict());
		addressDTO.setCity(address.getCity());
		addressDTO.setState(address.getState());
		addressDTO.setRegion(address.getRegion());
		
		return addressDTO;
	}

	public Address addressDTOToAddress(AddressDTO addressDTO) 
	{
		Address address = new Address();
		address.setId(addressDTO.getId());
		address.setStreet(addressDTO.getStreet());
		address.setDistrict(addressDTO.getDistrict());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		address.setRegion(addressDTO.getRegion());
		
		return address;
	}

	public Address updateAdress(Address address, AddressDTO addressDTO)
	{
		address.setStreet(addressDTO.getStreet());
		address.setDistrict(addressDTO.getDistrict());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		address.setRegion(addressDTO.getRegion());
		
		return address;
	}

}
