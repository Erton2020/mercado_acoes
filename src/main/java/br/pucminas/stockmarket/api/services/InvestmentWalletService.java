package br.pucminas.stockmarket.api.services;

import br.pucminas.stockmarket.api.entities.InvestmentWallet;

public interface InvestmentWalletService {

	InvestmentWallet findInvestmentWalletByInvestorId(Long investorId);

}
