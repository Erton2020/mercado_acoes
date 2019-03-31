package br.pucminas.stockmarket.api.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.pucminas.stockmarket.api.enums.InvestmentHistoricalTypeEnum;
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
@Entity
public class InvestmentHistorical 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Investment investment;
	
	@Enumerated(value = EnumType.STRING)
	private InvestmentHistoricalTypeEnum InvestmentHistoricalType;
	
	private Long quantity;
	
	private Double value;
	
	private Calendar  creationDate;
}
