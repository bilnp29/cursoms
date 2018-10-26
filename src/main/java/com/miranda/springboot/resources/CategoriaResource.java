package com.miranda.springboot.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miranda.springboot.domain.Categoria;
import com.miranda.springboot.services.CategoriaService;

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
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	/**
	 * Método recebe uma requisição da aplicação, onde esta irá repassa a informação
	 * para a camanda de servico que irá realizar a busca pelo id.
	 * 
	 * Método de busca pelo id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	
	/**
	 * Método recebe um objeto do tipo categoria, chama a função insert, onde esta irá salva 
	 * as informações no banco de dados.
	 * 
	 * Método para inserir
	 * @param obj
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Método modifica e atualizar dados da categoria.
	 * @param obj
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> upDate(@RequestBody Categoria obj,@PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método responsavel para deletar uma categoria a parti do id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
}
