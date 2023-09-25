package com.bruno.helpdesk.services;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.helpdesk.domain.Pessoa;
import com.bruno.helpdesk.domain.Tecnico;
import com.bruno.helpdesk.domain.dtos.TecnicoDTO;
import com.bruno.helpdesk.repositories.PessoaRepository;
import com.bruno.helpdesk.repositories.TecnicoRepository;
import com.bruno.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.bruno.helpdesk.services.exceptions.ObjectNotFoundExeception;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		return tecnicoRepository.findById(id).
				orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado: " + id));
	}
	
	public List<Tecnico> findAll(){
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		validarCpfEEmail(tecnicoDTO);
		Tecnico tecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(tecnico);
	}

	private void validarCpfEEmail(TecnicoDTO tecnicoDTO) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
		if(pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado.");
		}
		pessoa = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
		if(pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado");
		}
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO newObj) {
		newObj.setId(id);
		Tecnico oldObj = findById(id);
		validarCpfEEmail(newObj);
		oldObj = new Tecnico(newObj);
		return tecnicoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O tecnico possui ordens de serviço e não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}
}
