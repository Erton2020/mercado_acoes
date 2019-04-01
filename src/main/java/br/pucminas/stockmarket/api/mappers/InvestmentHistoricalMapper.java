package br.pucminas.stockmarket.api.mappers;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.InvestmentHistoricalDTO;
import br.pucminas.stockmarket.api.entities.InvestmentHistorical;

@Component
public class InvestmentHistoricalMapper {

	public InvestmentHistoricalDTO investmentHistoricalTOInvestmentHistoricalDTO(InvestmentHistorical investmentHistorical) 
	{
		InvestmentHistoricalDTO investmentHistoricalDTO = new InvestmentHistoricalDTO();
		investmentHistoricalDTO.setId(investmentHistorical.getId());
		investmentHistoricalDTO.setQuantity(investmentHistorical.getQuantity());
		investmentHistoricalDTO.setUnitStockValue(investmentHistorical.getUnitStockValue());
		investmentHistoricalDTO.setCreationDate(investmentHistorical.getCreationDate());

		return investmentHistoricalDTO;
	}

}
