package com.miranda.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miranda.springboot.domain.Categoria;
import com.miranda.springboot.repositories.CategoriaRepository;

/**
 * @author Bruno Miranda
 * 
 * Classe de servicos a mesma esta destina da  se comunicar com as camadas de repositorio, dominio e aplicação.
 * aqui estão presente as regras de negocio desta aplicação.
 * 
 */

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;
	
	/**
	 *Realizar busca pela categoria de acordo com o id.
	 */
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repositorio.findById(id);
		return obj.orElse(null);
	}
}
