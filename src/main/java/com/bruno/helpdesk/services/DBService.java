package com.bruno.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.helpdesk.domain.Chamado;
import com.bruno.helpdesk.domain.Cliente;
import com.bruno.helpdesk.domain.Tecnico;
import com.bruno.helpdesk.domain.enums.Perfil;
import com.bruno.helpdesk.domain.enums.Prioridade;
import com.bruno.helpdesk.domain.enums.Status;
import com.bruno.helpdesk.repositories.ChamadoRepository;
import com.bruno.helpdesk.repositories.ClienteRepository;
import com.bruno.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {

		Tecnico tecnico1 = new Tecnico(null, "Bruno Bandeira", "012.123.434-23", "brunobandeira20@gmail.com", "123");
		tecnico1.addPerfis(Perfil.ADMIN);

		Cliente cliente1 = new Cliente(null, "Maria das dores", "10230405066", "maria@gmail.com", "123321");

		Chamado chamado1 = new Chamado(null, Prioridade.ALTA, Status.EM_ANDAMENTO, "Chamado 01", "Primeiro Chamado",
				cliente1, tecnico1);

		tecnicoRepository.saveAll(Arrays.asList(tecnico1));
		clienteRepository.saveAll(Arrays.asList(cliente1));
		chamadoRepository.saveAll(Arrays.asList(chamado1));

	}

}
