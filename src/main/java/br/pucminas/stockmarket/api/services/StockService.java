package br.pucminas.stockmarket.api.services;

import java.util.List;

import br.pucminas.stockmarket.api.dto.StockDTO;

public interface StockService {

	List<StockDTO> findStocksByCompanyId(Long companyId);

}
