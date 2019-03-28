package br.pucminas.stockmarket.api.services;

import java.util.List;

import br.pucminas.stockmarket.api.entities.Address;

public interface AddressService {

	Address insert(Address address);
	
	List<Address> insertAll(List<Address> addresses);

	Address update(Address address);

}
