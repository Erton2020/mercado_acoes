package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

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
	
	private Long amountStockPurchased;
	
	private Double purchaseAverageValue;

	private Long amountStockSold;
	
	private Double saleAverageValue;
	
	private Double averageProfitValue;
}
