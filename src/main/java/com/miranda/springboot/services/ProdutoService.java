package com.miranda.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.miranda.springboot.domain.Categoria;
import com.miranda.springboot.domain.Produto;
import com.miranda.springboot.repositories.CategoriaRepository;
import com.miranda.springboot.repositories.ProdutoRepository;
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
public class ProdutoService {

	@Autowired
	private ProdutoRepository ProdutoRepositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 * Realizar busca pela categoria de acordo com o id.
	 */
	public Produto find(Integer id) {
		Optional<Produto> obj = ProdutoRepositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesParPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesParPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias =  categoriaRepository.findAllById(ids);
		return ProdutoRepositorio.search(nome,categorias,pageRequest);
	}
}
