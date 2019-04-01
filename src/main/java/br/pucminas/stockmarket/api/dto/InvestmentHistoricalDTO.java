package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentHistoricalDTO 
{
	private Long id;
	
	private Long quantity;
	
	private Double unitStockValue;
	
	private Calendar creationDate;
}
