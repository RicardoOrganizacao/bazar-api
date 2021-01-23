package com.ricbap.bazar.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ricbap.bazar.domain.Categoria;
import com.ricbap.bazar.dto.CategoriaDTO;
import com.ricbap.bazar.repositories.CategoriaRepository;
import com.ricbap.bazar.services.exceptions.DataIntegrityException;
import com.ricbap.bazar.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				  "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // <--- O id tem que estar null para inserir uma nova categoria
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); // <-- Chama o metodo find, caso o objeto com esse id nao exista ele lança uma exceção
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria que possui Produtos");
		}
	}
	
	public List<CategoriaDTO> findAll() {
		List<Categoria> list = repo.findAll();
		List<CategoriaDTO> listDto = toCollectionMode(list);
		//List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
				
		return listDto;
	}
	
	private List<CategoriaDTO> toCollectionMode(List<Categoria> list) {
		return list.stream()
				.map(obj -> toModel(obj))
				.collect(Collectors.toList());
	}
	
	private CategoriaDTO toModel(Categoria obj) {
		return modelMapper.map(obj, CategoriaDTO.class);
	}


}


  
 
