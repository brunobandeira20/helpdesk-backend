package com.bruno.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.helpdesk.domain.Tecnico;
import com.bruno.helpdesk.repositories.TecnicoRepository;
import com.bruno.helpdesk.services.exceptions.ObjectNotFoundExeception;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		return tecnicoRepository.findById(id).
				orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado: " + id));
	}
}
