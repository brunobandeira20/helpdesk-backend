package com.bruno.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.helpdesk.domain.Pessoa;
import com.bruno.helpdesk.domain.Cliente;
import com.bruno.helpdesk.domain.dtos.ClienteDTO;
import com.bruno.helpdesk.repositories.PessoaRepository;
import com.bruno.helpdesk.repositories.ClienteRepository;
import com.bruno.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.bruno.helpdesk.services.exceptions.ObjectNotFoundExeception;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		return clienteRepository.findById(id).
				orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado: " + id));
	}
	
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		validarCpfEEmail(clienteDTO);
		Cliente cliente = new Cliente(clienteDTO);
		return clienteRepository.save(cliente);
	}

	private void validarCpfEEmail(ClienteDTO clienteDTO) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDTO.getCpf());
		if(pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado.");
		}
		pessoa = pessoaRepository.findByEmail(clienteDTO.getEmail());
		if(pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado");
		}
	}

	public Cliente update(Integer id, @Valid ClienteDTO newObj) {
		newObj.setId(id);
		Cliente oldObj = findById(id);
		validarCpfEEmail(newObj);
		oldObj = new Cliente(newObj);
		return clienteRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O cliente possui ordens de serviço e não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}
}
