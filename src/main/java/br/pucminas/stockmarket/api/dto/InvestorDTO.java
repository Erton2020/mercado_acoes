package br.pucminas.stockmarket.api.dto;

import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvestorDTO 
{	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String investorType;
	
	@NotNull
	private List<AddressDTO> addresses;
	
	private Calendar creationDate;
}
