package br.pucminas.mercado_acoes.api.entities;

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
public class Endereco {

	private Long id;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String pais;
}
