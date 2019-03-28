package br.pucminas.stockmarket.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucminas.stockmarket.api.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
