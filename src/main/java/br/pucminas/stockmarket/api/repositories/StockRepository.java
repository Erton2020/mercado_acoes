package br.pucminas.stockmarket.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pucminas.stockmarket.api.entities.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{

	List<Stock> findAllStocksByCompanyId(Long companyId);

}
