package br.pucminas.mercado_acoes.api.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.pucminas.mercado_acoes.api.enums.StatusAcao;
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
public class Acao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Empresa empresa;
	private String descricao;
	private StatusAcao statusAcao;
	private Calendar dataCriacao;
	private Double valorInicial;
	private Acionista acionista;
	private Calendar dataUltimaAtualizacao;
}
