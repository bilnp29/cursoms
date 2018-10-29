package com.miranda.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.miranda.springboot.domain.Categoria;
import com.miranda.springboot.repositories.CategoriaRepository;
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
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;

	/**
	 * Realizar busca pela categoria de acordo com o id.
	 */
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", tipo: " + Categoria.class.getName()));
	}

	/**
	 * Inserindo um objeto categoria
	 * 
	 * @param obj
	 * @return
	 */
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repositorio.save(obj);
	}

	/**
	 * Atualizar ou modifica uma categoria
	 * @param obj
	 * @return
	 */
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repositorio.save(obj);
	}

	/**
	 * Exclui uma categoria pelo id.
	 * @param id
	 */
	public void delete(Integer id) {
		find(id);
		try {
		repositorio.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	
	}

	/**
	 * Retorna uma lista de categoria quando soliciato
	 * @return lista Categorias
	 */
	public List<Categoria> findAll() {
		
		return repositorio.findAll();
	}
	
	/**
	 * Método de lista categorias com paginação. Utilizando a biblioteca Spring Data.
	 * 
	 * @param page quantidade de páginas
	 * @param linesParPage tamanho da página
	 * @param orderBy filtro utilizado pela páginação podendo ser (id, nome...)
	 * @param direction direção de como as páginas serão renderizadas (ascendente ou descendente)
	 * @return retorna a ou as páginas desejadas.
	 */
	public Page<Categoria> findPage(Integer page, Integer linesParPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesParPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
			
	}
}
