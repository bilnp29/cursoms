package com.miranda.springboot.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.miranda.springboot.domain.enums.EstadoPagamento;
@Entity
public class PagamentoComBoleto extends Pagamento { 

	private static final long serialVersionUID = 1L;
	
	private Date dataVencimento;
	private Date dataPagamanto;
	
	public PagamentoComBoleto() {
		
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamanto = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamanto() {
		return dataPagamanto;
	}

	public void setDataPagamanto(Date dataPagamanto) {
		this.dataPagamanto = dataPagamanto;
	}
	
	
}
