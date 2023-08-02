package com.bruno.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.helpdesk.domain.Chamado;
import com.bruno.helpdesk.repositories.ChamadoRepository;
import com.bruno.helpdesk.services.exceptions.ObjectNotFoundExeception;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	public Chamado findById(Integer id) {
		return chamadoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundExeception("Chamado nao encontrado"));
	}
	
}
