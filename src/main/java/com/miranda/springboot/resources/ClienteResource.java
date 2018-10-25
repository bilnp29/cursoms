package com.miranda.springboot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miranda.springboot.domain.Cliente;
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
	 * Método recebe uma requisição da aplicação onde esta irá repassa a informação
	 * para a camanda de servico que irá realizar a busca pelo id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
}
