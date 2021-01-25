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
	/*
	public Categoria insert(Categoria obj) {
		obj.setId(null); // <--- O id tem que estar null para inserir uma nova categoria
		return repo.save(obj);
	}*/
	
	public Categoria insert(CategoriaDTO objDto) {
		objDto.setId(null); // <--- O id tem que estar null para inserir uma nova categoria
		Categoria obj = toEntity(objDto);
		return repo.save(obj);
	}
	
	public Categoria update(CategoriaDTO objDto) {
		Categoria newObj = find(objDto.getId()); // <-- Chama o metodo find, caso o objeto com esse id nao exista ele lança uma exceção
		BeanUtils.copyProperties(objDto, newObj, "codigo");
		//Categoria obj = toEntity(objDto);
		return repo.save(newObj);
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
		List<CategoriaDTO> listDto = toCollectionModel(list);
		//List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());				
		return listDto;
	}
	
	public Page<CategoriaDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Categoria> list =  repo.findAll(pageRequest);
		Page<CategoriaDTO> listDto = toCollectionModelPage(list);
		return listDto;
	} 
	
	private Page<CategoriaDTO> toCollectionModelPage(Page<Categoria> list) {
		return list.map(obj -> toModel(obj));
	}
	
	private List<CategoriaDTO> toCollectionModel(List<Categoria> list) {
		return list.stream()
				.map(obj -> toModel(obj))
				.collect(Collectors.toList());
	}
	
	private CategoriaDTO toModel(Categoria obj) {
		return modelMapper.map(obj, CategoriaDTO.class);
	}
	
	/*
	private Categoria fromDto(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	} */
	
	private Categoria toEntity(CategoriaDTO objDto) {
		return modelMapper.map(objDto, Categoria.class);
	}


}

/*
  SQL
  SELECT DISTINCT * FROM produto
  INNER JOIN produto_categoria cat1 ON produto.id = cat1.produto_id
  INNER JOIN categoria cat2 ON cat1.categoria.id = cat2.id
  WHERE produto.nome LIKE ? AND cat2_id IN (?, ?)
  
  JPQL
  SELECT DISTINCT obj FROM Produto obj
  INNER JOIN obj.categorias cat
  WHERE obj.nome LIKE %:nome% AND cat IN :categorias
 */
