package com.miranda.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miranda.springboot.domain.Cidade;
import com.miranda.springboot.domain.Cliente;
import com.miranda.springboot.domain.Endereco;
import com.miranda.springboot.domain.enums.TipoCliente;
import com.miranda.springboot.dto.ClienteDTO;
import com.miranda.springboot.dto.ClienteNewDTO;
import com.miranda.springboot.repositories.ClienteRepository;
import com.miranda.springboot.repositories.EnderecoRepository;
import com.miranda.springboot.services.exceptions.DataIntegrityException;
import com.miranda.springboot.services.exceptions.ObjectNotFoundException;

/**
 * @author Bruno Miranda
 * 
 *         Classe de servicos a mesma esta destina da se comunicar com as
 *         camadas de clienteRepositorio, dominio e aplicação. aqui estão presente as
 *         regras de negocio desta aplicação.
 * 
 */

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepositorio;
	
	@Autowired
	private EnderecoRepository enderecoRepositorio;

	/**
	 * Realizar busca pela Cliente de acordo com o id.
	 */
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", tipo: " + Cliente.class.getName()));
	}

	/**
	 * Atualizar ou modifica uma cliente
	 * 
	 * Captura um cliente aparti do método find repassa os dados para um novo objeto
	 * do tipo clinte. A atualização só será feita em alguns atributos do cliente.
	 * 
	 * @param obj
	 * @return
	 */
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepositorio.save(newObj);
	}

	/**
	 * Inserindo um objeto categoria
	 * 
	 * @param obj
	 * @return
	 */
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepositorio.save(obj);
		enderecoRepositorio.saveAll(obj.getEnderecos());
		return obj;
	}

	/**
	 * Método auxiliar para atualização do cliente
	 * 
	 * @param newObj Novo objeto cliente, os dados serão atualizados no método.
	 * @param obj    Objeto antigo
	 */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());

	}

	/**
	 * Exclui uma cliente pelo id.
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		find(id);
		try {
			clienteRepositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma cliente, existe pedidos relacionado.");
		}

	}

	/**
	 * Retorna uma lista de cliente quando soliciato
	 * 
	 * @return lista Clientes
	 */
	public List<Cliente> findAll() {

		return clienteRepositorio.findAll();
	}

	/**
	 * Método de lista clientes com paginação. Utilizando a biblioteca Spring Data.
	 * 
	 * @param page         quantidade de páginas
	 * @param linesParPage tamanho da página
	 * @param orderBy      filtro utilizado pela páginação podendo ser (id, nome...)
	 * @param direction    direção de como as páginas serão renderizadas (ascendente
	 *                     ou descendente)
	 * @return retorna a ou as páginas desejadas.
	 */
	public Page<Cliente> findPage(Integer page, Integer linesParPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesParPage, Direction.valueOf(direction), orderBy);
		return clienteRepositorio.findAll(pageRequest);

	}

	/**
	 * Método auxiliar que instancia um Cliente aparti de um objeto DTO
	 * 
	 * @param objDto
	 * @return
	 */
	public Cliente fromDTO(ClienteDTO objDto) {

		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	/**
	 * Método auxiliar que instancia um Cliente aparti de um objeto DTO (ClienteNewDTO) o mesmo
	 * é acionado quando um cliente é inserido na base de dados. 
	 * @param objDto instancia da classe ClienteNewDTO
	 * @return Retorna um cliente.
	 */
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfouCnpj(),
				TipoCliente.toEnum(objDto.getTipo()));
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDto.getLogradoro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone1());
		
		if(objDto.getTelefone2() != null) {
			cliente.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cliente.getTelefones().add(objDto.getTelefone3());
		}
		return cliente;
	}
}
