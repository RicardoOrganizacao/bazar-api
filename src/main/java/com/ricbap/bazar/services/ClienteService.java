package com.ricbap.bazar.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ricbap.bazar.domain.Cliente;
import com.ricbap.bazar.dto.ClienteDTO;
import com.ricbap.bazar.repositories.ClienteRepository;
import com.ricbap.bazar.services.exceptions.DataIntegrityException;
import com.ricbap.bazar.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				  "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(ClienteDTO objDto) {
		Cliente newObj = find(objDto.getId()); // <-- Chama o metodo find, caso o objeto com esse id nao exista ele lança uma exceção
		//updateData(newObj, objDto);
		BeanUtils.copyProperties(objDto, newObj, "codigo", "cpfOuCnpj", "tipo");
		//Cliente obj = toEntity(newObj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Cliente que possui Pedidos");
		}
	}
	
	public List<ClienteDTO> findAll() {
		List<Cliente> list = repo.findAll();
		List<ClienteDTO> listDto = toCollectionModel(list);
		//List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());				
		return listDto;
	}
	
	public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Cliente> list =  repo.findAll(pageRequest);
		Page<ClienteDTO> listDto = toCollectionModelPage(list);
		return listDto;
	} 
	
	private Page<ClienteDTO> toCollectionModelPage(Page<Cliente> list) {
		return list.map(obj -> toModel(obj));
	}
	
	private List<ClienteDTO> toCollectionModel(List<Cliente> list) {
		return list.stream()
				.map(obj -> toModel(obj))
				.collect(Collectors.toList());
	}
	
	private ClienteDTO toModel(Cliente obj) {
		return modelMapper.map(obj, ClienteDTO.class);
	}
	
	/*
	private Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	} 
	
	private Cliente toEntity(Cliente newObj) {
		return modelMapper.map(newObj, Cliente.class);		
	}
	
	private void updateData(Cliente newObj, ClienteDTO objDto) {
		newObj.setNome(objDto.getNome());
		newObj.setEmail(objDto.getEmail());
	} */
	
	
}
	


  
 
