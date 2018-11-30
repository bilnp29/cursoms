package com.miranda.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miranda.springboot.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	/**
	 * Buscar por email, a lógica implementada pelo proprio fremework.
	 * @param email válido
	 * @return o email procurado.
	 */
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
