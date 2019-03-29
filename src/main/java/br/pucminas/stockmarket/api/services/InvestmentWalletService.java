package br.pucminas.stockmarket.api.services;

import java.util.Optional;

import br.pucminas.stockmarket.api.entities.InvestmentWallet;

public interface InvestmentWalletService {

	Optional<InvestmentWallet> findInvestmentWalletByInvestorId(Long investorId);

	InvestmentWallet insert(InvestmentWallet investmentWallet);

}
