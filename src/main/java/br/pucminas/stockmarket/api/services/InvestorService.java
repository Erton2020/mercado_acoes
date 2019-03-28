package br.pucminas.stockmarket.api.services;

import java.util.List;
import java.util.Optional;

import br.pucminas.stockmarket.api.dto.InvestorDTO;
import br.pucminas.stockmarket.api.entities.Investor;

public interface InvestorService {

	List<InvestorDTO> findAllInvestorsDTO();

	Optional<Investor> findInvestorById(Long investorId);

	Investor insert(Investor investor);

	Investor update(Investor investor);

}
