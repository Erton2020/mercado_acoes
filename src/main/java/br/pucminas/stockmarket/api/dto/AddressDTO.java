package br.pucminas.stockmarket.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
	
	private Long id;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private String district;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@NotBlank
	private String region;
}
