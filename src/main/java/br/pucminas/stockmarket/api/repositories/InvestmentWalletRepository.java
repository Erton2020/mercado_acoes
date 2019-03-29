package br.pucminas.stockmarket.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pucminas.stockmarket.api.entities.InvestmentWallet;

@Repository
public interface InvestmentWalletRepository extends JpaRepository<InvestmentWallet, Long>
{

	Optional<InvestmentWallet> findInvestmentWalletByInvestorId(Long investorId);

}
