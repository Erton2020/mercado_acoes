package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentDTO 
{
	private Long id;
	
	private Calendar creationDate;
	
	private Long companyId;
	
	private String companyName;
	
	private Long stockId;
	
	private String stockDescription;

	private String stockType;
	
	private Long amountCurrentStock;

	private Double currentStockValue;
	
	private Long amountOfStockInWallet;
	
	private Double purchaseAverageUnitStockValue;

	private Long amountStockSold;
	
	private Double saleAverageUnitStockValue;
	
	private List<InvestmentHistoricalDTO> purchasesHistorical;
	
	private List<InvestmentHistoricalDTO> salesHistorical;
}
