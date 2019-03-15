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
public class Address {

	private Long id;
	private String street;
	private String district;
	private String city;
	private String state;
	private String region;
	private Calendar creationDate;
	private Calendar lastUpdate;
}
