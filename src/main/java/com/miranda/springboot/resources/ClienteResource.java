package com.miranda.springboot.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miranda.springboot.domain.Cliente;
import com.miranda.springboot.dto.ClienteDTO;
import com.miranda.springboot.dto.ClienteNewDTO;
import com.miranda.springboot.services.ClienteService;

/**
 * @author Bruno
 * 
 *         Classe destinada a comunuca-se com as camadas de aplicação e servico
 *         da classe repositorio.
 * 
 *         Controller
 *
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	/**
	 * Buscando um cliente pelo Id
	 * 
	 * Método recebe uma requisição da aplicação onde esta irá repassa a informação
	 * para a camanda de servico que irá realizar a busca pelo id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/**
	 * 
	 * 
	 * Método recebe um objeto do tipo categoria, chama a função insert, onde esta
	 * irá salva as informações no banco de dados.
	 * 
	 * @param obj
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto); // Convertendo para um objeto ResponseEntity
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	
	/**
	 * Método modifica e atualizar dados de um Cliente.
	 * 
	 * @param obj
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> upDate(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto); // Convertendo para um objeto ResponseEntity
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Método responsavel para deletar uma cliente a parti do id.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	/**
	 * O método em questão irá devolver uma lista de clientes
	 * 
	 * @return uma lista de categoria
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	/**
	 * Método de busca por páginação, endpont Page<Cliente>
	 * 
	 * @param page         quantidade de páginas
	 * @param linesParPage tamanho da página
	 * @param orderBy      filtro utilizado pela páginação podendo ser (id, nome...)
	 * @param direction    direção de como as páginas serão renderizadas (ascendente
	 *                     ou descendente)
	 * @return retorna á ou ás páginas desejadas.
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesParPage", defaultValue = "24") Integer linesParPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = service.findPage(page, linesParPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	
}
