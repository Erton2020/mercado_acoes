package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaleOrderDTO {
	
	private Long id;
	
	@NotNull
	private Long investorId;
	
	private String investorName;
	
	private Long companyId;
	
	private String companyName;
	
	@NotNull
	private Long stockId;
	
	private String stockDescription;
	
	@NotNull
	private Long amount;
	
	private Calendar saleDate;
	
	private Double unitSalePrice;
}
