package br.pucminas.stockmarket.api.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
public class Investment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Calendar creationDate;
	
	@ManyToOne
	private InvestmentWallet investmentWallet;
	
	@OneToOne
	private Stock stock;
	
	@OneToMany
	List<InvestmentHistorical> InvestmentsHistorical;
	
	private Calendar lastUpdate;
	
}
