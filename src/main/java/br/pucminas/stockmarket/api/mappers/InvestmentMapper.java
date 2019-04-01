package br.pucminas.stockmarket.api.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.pucminas.stockmarket.api.dto.InvestmentDTO;
import br.pucminas.stockmarket.api.dto.InvestmentHistoricalDTO;
import br.pucminas.stockmarket.api.entities.Investment;
import br.pucminas.stockmarket.api.entities.InvestmentHistorical;
import br.pucminas.stockmarket.api.enums.CalculationTypeEnum;
import br.pucminas.stockmarket.api.enums.InvestmentHistoricalTypeEnum;
import br.pucminas.stockmarket.api.services.StockService;

@Component
public class InvestmentMapper 
{
	InvestmentHistoricalMapper investmentHistoricalMapper;
	StockService stockService;
	
	public InvestmentMapper(InvestmentHistoricalMapper p_investmentHistoricalMapper, StockService p_stockService) 
	{
		this.investmentHistoricalMapper = p_investmentHistoricalMapper;
		this.stockService = p_stockService;
	}
	
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

			
			if(investment.getInvestmentsHistorical()!=null && investment.getInvestmentsHistorical().size() > 0)
			{
				int purchaseCount = 0;
				int saleCount = 0;
				Double purchasesUnitTotalValue = 0.0;
				Double salesUnitTotalValue = 0.0;
				Long purchasesStockQuantity = 0l;
				Long saleStockQuantity = 0l;
				List<InvestmentHistoricalDTO> purchasesHistoricalDTO = new ArrayList<InvestmentHistoricalDTO>();
				List<InvestmentHistoricalDTO> salesHistoricalDTO = new ArrayList<InvestmentHistoricalDTO>();
				for (InvestmentHistorical item :  investment.getInvestmentsHistorical())
				{
					if(item.getInvestmentHistoricalType().equals(InvestmentHistoricalTypeEnum.PURCHASE))
					{
						purchaseCount++;
						purchasesUnitTotalValue += item.getUnitStockValue();
						purchasesStockQuantity += item.getQuantity();
						
						purchasesHistoricalDTO.add(investmentHistoricalMapper.investmentHistoricalTOInvestmentHistoricalDTO(item));
					}
					else
					{
						saleCount++;
						salesUnitTotalValue += item.getUnitStockValue();
						saleStockQuantity += item.getQuantity();
						
						salesHistoricalDTO.add(investmentHistoricalMapper.investmentHistoricalTOInvestmentHistoricalDTO(item));
					}
				}
				
				investmentDTO.setAmountCurrentStock(purchasesStockQuantity - saleStockQuantity);
				investmentDTO.setCurrentStockValue(stockService.calculateStockCurrentValue(investment.getStock().getHistoricalStockPrices(), CalculationTypeEnum.CURRENT_VALUE));
				investmentDTO.setAmountOfStockInWallet(purchasesStockQuantity);
				
				Double purchaseAverageUnitStockValue = purchaseCount > 1 ? (purchasesUnitTotalValue / purchaseCount) : purchasesUnitTotalValue;
				investmentDTO.setPurchaseAverageUnitStockValue(purchaseAverageUnitStockValue);
				
				investmentDTO.setAmountStockSold(saleStockQuantity);
				Double saleAverageUnitStockValue = saleCount > 1 ? (salesUnitTotalValue / saleCount) : salesUnitTotalValue;
				investmentDTO.setSaleAverageUnitStockValue(saleAverageUnitStockValue);
				
				investmentDTO.setPurchasesHistorical(purchasesHistoricalDTO);
				investmentDTO.setSalesHistorical(salesHistoricalDTO);
			}
			
			investmentsDTO.add(investmentDTO);
		}
		
		return investmentsDTO;
	}
}
