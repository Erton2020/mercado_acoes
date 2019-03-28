package br.pucminas.stockmarket.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pucminas.stockmarket.api.entities.Investor;

@Repository
public interface InvestorRepository  extends JpaRepository<Investor, Long>
{

}
