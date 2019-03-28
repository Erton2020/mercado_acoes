package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO
{	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private AddressDTO address;
	
	private Calendar creationDate;
}
