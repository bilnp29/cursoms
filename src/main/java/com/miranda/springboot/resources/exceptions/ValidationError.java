package com.miranda.springboot.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *	Classe auxiliar para descrever o erro quando for inserir ou atualizar uma categoria.
 *
 *  @author Bruno Miranda
 *
 */
public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String messagem) {
		erros.add(new FieldMessage(fieldName, messagem));
	}

}
