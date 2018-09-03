package com.miranda.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miranda.springboot.domain.Pedido;
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

	/**
	 * Realizar busca pela categoria de acordo com o id.
	 */
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", tipo: " + Pedido.class.getName()));
	}
}
