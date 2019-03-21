package br.pucminas.stockmarket.api.entities;

import java.util.Calendar;

import javax.persistence.Entity;

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
public class InvestmentWallet {

	private Long id;
	private Calendar creationDate;
	private Investor investor;
	private Calendar lastUpdate;
}
