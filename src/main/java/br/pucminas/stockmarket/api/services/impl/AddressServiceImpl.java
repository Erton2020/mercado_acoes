package br.pucminas.stockmarket.api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.entities.Address;
import br.pucminas.stockmarket.api.repositories.AddressRepository;
import br.pucminas.stockmarket.api.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService
{
	AddressRepository addressRepository;
	
	public AddressServiceImpl(AddressRepository p_addressRepository) 
	{
		this.addressRepository = p_addressRepository;
	}

	@Override
	public Address insert(Address address) 
	{
		return addressRepository.save(address);
	}
	
	@Override
	public List<Address> insertAll(List<Address> addresses) 
	{
		return addressRepository.saveAll(addresses);
	}

	@Override
	public Address update(Address address) 
	{
		return addressRepository.save(address);
	}
}
