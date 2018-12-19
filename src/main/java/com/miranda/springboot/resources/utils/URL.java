package com.miranda.springboot.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	/**
	 * Método recebe parte da String da URL que faz busca do produto com paginação.
	 * Verificar classe ProdutoResource método findPage.
	 *
	 **/
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		// Podemos utilizar lambda veja abaixo.

		// return Arrays.asList(s.split(",")).stream().map(x ->
		// Integer.parseInt(x)).collect(Collectors.toList());
	}

	/**
	 * Método decodifica o nome da URI, caso o usuario passe o mesmo com caracteres
	 * especial.
	 */
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
