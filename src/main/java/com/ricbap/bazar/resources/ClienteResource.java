package com.ricbap.bazar.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ricbap.bazar.domain.Cliente;
import com.ricbap.bazar.dto.ClienteDTO;
import com.ricbap.bazar.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {		
		Cliente obj = service.find(id);						
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto) {
		objDto.setId(id); // <--- Garantir que o objeto tenha o id
		service.update(objDto);
		return ResponseEntity.noContent().build(); // 204 - Conteudo vazio
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listDto = service.findAll();
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<ClienteDTO> listDto = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(listDto);
	}
	

}
