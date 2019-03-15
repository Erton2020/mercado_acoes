package br.pucminas.mercado_acoes.api.entities;

import java.util.Calendar;

import br.pucminas.mercado_acoes.api.enums.StatusOrdemCompra;

public class OrdemCompra {
	
	private Long id;
	private Calendar dataCriacao;
	private StatusOrdemCompra statusOrdemCompra;
	private Acao acao;
	private Double valorCompra;
	private Calendar dataUltimaAtualizacao;
}
