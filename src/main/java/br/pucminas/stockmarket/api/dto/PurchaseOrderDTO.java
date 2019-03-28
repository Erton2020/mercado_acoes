package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderDTO 
{
	private Long id;
	
	@NotNull
	private Long investorId;
	
	private String investorName;
	
	private Long companyId;
	
	private String companyName;
	
	private Long stockId;
	
	private String stockDescription;
	
	@NotNull
	private Double amount;
	
	private Calendar purchaseDate;
	
	private Double unitPurchasePrice;
}
