package br.pucminas.stockmarket.api.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;

import br.pucminas.stockmarket.api.enums.InvestorTypeEnum;
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
public class Investor 
{
	private Long id;
	private String name;
	private InvestorTypeEnum investorType;
	private Address address;
	private Calendar creationDate;
	private List<CompanyStock> companyStock;
}
