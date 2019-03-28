package br.pucminas.stockmarket.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pucminas.stockmarket.api.entities.InvestmentWallet;

@Repository
public interface InvestmentWalletRepository extends JpaRepository<InvestmentWallet, Long>
{

	InvestmentWallet findInvestmentWalletByInvestorId(Long investorId);

}
