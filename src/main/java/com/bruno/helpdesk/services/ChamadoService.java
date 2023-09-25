package com.bruno.helpdesk.services;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.helpdesk.domain.Chamado;
import com.bruno.helpdesk.domain.Cliente;
import com.bruno.helpdesk.domain.Tecnico;
import com.bruno.helpdesk.domain.dtos.ChamadoDTO;
import com.bruno.helpdesk.domain.enums.Prioridade;
import com.bruno.helpdesk.domain.enums.Status;
import com.bruno.helpdesk.repositories.ChamadoRepository;
import com.bruno.helpdesk.services.exceptions.ObjectNotFoundExeception;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired 
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		return chamadoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundExeception("Chamado nao encontrado"));
	}
	
	public List<Chamado> findAll(){
		return chamadoRepository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO obj) {
		Chamado newOjb = newChamado(obj);
		return chamadoRepository.save(newOjb);
	}
	
	private Chamado newChamado(ChamadoDTO obj){
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		if(obj.getStatus() == Status.ENCERRADO.getCodigo()){
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setCliente(cliente);
		chamado.setTecnico(tecnico);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}

	public Chamado update(Integer id, ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);		
		return chamadoRepository.save(oldObj);
	}
}
