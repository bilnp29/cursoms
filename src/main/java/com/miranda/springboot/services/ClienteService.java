package com.miranda.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.miranda.springboot.domain.Cliente;
import com.miranda.springboot.dto.ClienteDTO;
import com.miranda.springboot.repositories.ClienteRepository;
import com.miranda.springboot.services.exceptions.DataIntegrityException;
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
public class ClienteService {

	@Autowired
	private ClienteRepository repositorio;

	/**
	 * Realizar busca pela Cliente de acordo com o id.
	 */
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", tipo: " + Cliente.class.getName()));
	}
	
	/**
	 * Atualizar ou modifica uma cliente
	 * 
	 * Captura um cliente aparti do método find repassa os dados para um novo objeto do tipo clinte.
	 * A atualização só será feita em alguns atributos do cliente.
	 * 
	 * @param obj
	 * @return
	 */
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repositorio.save(newObj);
	}

	/**
	 * Método auxiliar para atualização do cliente
	 * 
	 * @param newObj Novo objeto cliente, os dados serão atualizados no método.
	 * @param obj Objeto antigo
	 */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}

	/**
	 * Exclui uma cliente pelo id.
	 * @param id
	 */
	public void delete(Integer id) {
		find(id);
		try {
		repositorio.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma cliente.");
		}
	
	}

	/**
	 * Retorna uma lista de cliente quando soliciato
	 * @return lista Clientes
	 */
	public List<Cliente> findAll() {
		
		return repositorio.findAll();
	}
	
	/**
	 * Método de lista clientes com paginação. Utilizando a biblioteca Spring Data.
	 * 
	 * @param page quantidade de páginas
	 * @param linesParPage tamanho da página
	 * @param orderBy filtro utilizado pela páginação podendo ser (id, nome...)
	 * @param direction direção de como as páginas serão renderizadas (ascendente ou descendente)
	 * @return retorna a ou as páginas desejadas.
	 */
	public Page<Cliente> findPage(Integer page, Integer linesParPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesParPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
			
	}
	
	/**
	 * Método auxiliar que instancia um Cliente aparti de um objeto DTO
	 * 
	 * @param objDto
	 * @return
	 */
	public Cliente fromDTO(ClienteDTO objDto) {
		
		return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(),null,null);
	}
}
