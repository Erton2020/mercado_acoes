package br.pucminas.stockmarket.api.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.InvestmentDTO;
import br.pucminas.stockmarket.api.entities.Investment;

@Component
public class InvestmentMapper 
{
	public List<InvestmentDTO> investmentsTOInvestmentsDTO(List<Investment> investments) 
	{
		List<InvestmentDTO> investmentsDTO = new ArrayList<InvestmentDTO>();
		for (Investment investment : investments) 
		{
			InvestmentDTO investmentDTO = new InvestmentDTO();
			investmentDTO.setId(investment.getId());
			investmentDTO.setCreationDate(investment.getCreationDate());
			investmentDTO.setCompanyId(investment.getStock().getCompany().getId());
			investmentDTO.setCompanyName(investment.getStock().getCompany().getName());
			investmentDTO.setStockId(investment.getStock().getId());
			investmentDTO.setStockDescription(investment.getStock().getDescription());
			investmentDTO.setStockType(investment.getStock().getStockType().toString());
			investmentDTO.setAmountCurrentStock(investment.getStockQuantity());
	/*		investmentDTO.setCurrentStockValue(currentStockValue);
			investmentDTO.setAmountStockPurchased(amountStockPurchased);
			investmentDTO.setPurchaseAverageValue(purchaseAverageValue);
			investmentDTO.setAmountStockSold(amountStockSold);
			investmentDTO.setSaleAverageValue(saleAverageValue);
			investmentDTO.setAverageProfitValue(averageProfitValue);*/
			
			investmentsDTO.add(investmentDTO);
		}
		
		return investmentsDTO;
	}
}
