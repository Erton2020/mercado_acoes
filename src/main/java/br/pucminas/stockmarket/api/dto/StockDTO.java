package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO 
{
	private Long id;
	
	@NotNull
	private Long companyId;
	
	private String companyName;
	
	@NotNull
	private String description;

	@NotNull
	private String stockType;
	
	@NotNull
	private Long stocksForSale;
	
	private Double creationValue;
	
	private Double currentValue;
	
	private Calendar creationDate;
}
