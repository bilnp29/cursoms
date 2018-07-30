package com.miranda.springboot.domain.enums;

 /**
 * @author Bruno
 *
 *	Implementação do enum do tipoCliente o mesmo pode ser Pessoa Física ou Jurídica.
 *	
 */
public enum TipoCliente {

	PESSOAFISICA(1,"Pessoa Física"),
	PESSOAJURIDICA(2,"Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
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
	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for(TipoCliente tc : TipoCliente.values() ) {
			if(cod.equals(tc.getCod())) {
				return tc;
			}
		}
		
		throw new IllegalArgumentException("Id invalido " + cod);
	}
}
