package com.miranda.springboot.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miranda.springboot.domain.ItemPedido;
import com.miranda.springboot.domain.PagamentoComBoleto;
import com.miranda.springboot.domain.Pedido;
import com.miranda.springboot.domain.enums.EstadoPagamento;
import com.miranda.springboot.repositories.ItemPedidoRepository;
import com.miranda.springboot.repositories.PagamentoRepository;
import com.miranda.springboot.repositories.PedidoRepository;
import com.miranda.springboot.services.exceptions.ObjectNotFoundException;

/**
 * @author Bruno Miranda
 * 
 *         Classe de servicos a mesma esta destina da se comunicar com as
 *         camadas de repositorio, dominio e aplicação. aqui estão presente as
 *         regras de negocio desta aplicação.
 * 
 */

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repositorio;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;

	/**
	 * Realizar busca pela categoria de acordo com o id.
	 */
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDETE);
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repositorio.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}
