package com.miranda.springboot.domain.enums;

public enum EstadoPagamento {

	PENDETE(1, "pendente"),
	QUITADO(2, "quitado"),
	CANCELADO(3, "cancelando" );
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	// Este método realizar uma busca pelo cod do tipo cliente.
	public static EstadoPagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for(EstadoPagamento ep : EstadoPagamento.values() ) {
			if(cod.equals(ep.getCod())) {
				return ep;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + cod);
	}
}

