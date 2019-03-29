package br.pucminas.stockmarket.api.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.pucminas.stockmarket.api.entities.InvestmentWallet;
import br.pucminas.stockmarket.api.repositories.InvestmentWalletRepository;
import br.pucminas.stockmarket.api.services.InvestmentWalletService;

@Service
public class InvestmentWalletServiceImpl implements InvestmentWalletService
{
	
	InvestmentWalletRepository investmentWalletRepository;
	
	public InvestmentWalletServiceImpl(InvestmentWalletRepository p_investmentWalletRepository) 
	{
		this.investmentWalletRepository = p_investmentWalletRepository;
	}

	@Override
	public Optional<InvestmentWallet> findInvestmentWalletByInvestorId(Long investorId) 
	{
		return investmentWalletRepository.findInvestmentWalletByInvestorId(investorId);
	}

	@Override
	public InvestmentWallet insert(InvestmentWallet investmentWallet)
	{
		return investmentWalletRepository.save(investmentWallet);
	}

}
