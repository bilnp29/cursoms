package com.miranda.springboot.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.miranda.springboot.domain.enums.TipoCliente;
import com.miranda.springboot.dto.ClienteNewDTO;
import com.miranda.springboot.resources.exceptions.FieldMessage;
import com.miranda.springboot.services.validation.util.ValidationCpf_Cnpj;

public class ClienteInsertValidation implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !ValidationCpf_Cnpj.isValidCPF(objDto.getCpfouCnpj())) {
			list.add(new FieldMessage("cpfouCnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !ValidationCpf_Cnpj.isValidCNPJ(objDto.getCpfouCnpj())) {
			list.add(new FieldMessage("cpfouCnpj", "CNPJ inválido"));
		}
		
		
		for (FieldMessage fieldMessage : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessagem())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}

}
