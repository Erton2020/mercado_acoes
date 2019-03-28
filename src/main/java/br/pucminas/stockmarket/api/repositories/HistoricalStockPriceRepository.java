package br.pucminas.stockmarket.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.pucminas.stockmarket.api.entities.HistoricalStockPrice;

@Repository
public interface HistoricalStockPriceRepository extends JpaRepository<HistoricalStockPrice, Long>
{

	List<HistoricalStockPrice> findAllHistoricalStockPriceByStockId(Long stockId);

}
