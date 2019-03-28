package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HistoricalStockPriceDTO {
	
	private Long id;
	
	private Double price;

	private Calendar referenceDate;
}
