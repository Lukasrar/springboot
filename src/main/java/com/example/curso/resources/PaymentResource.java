package com.example.curso.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.curso.dto.PaymentDTO;
import com.example.curso.dto.PaymentDTO;
import com.example.curso.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {
	
	@Autowired
	private PaymentService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping 
	public ResponseEntity<List<PaymentDTO>> findAll(){

		List<PaymentDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<PaymentDTO> findById(@PathVariable Long id) {
		PaymentDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PaymentDTO> insert(@RequestBody PaymentDTO dto) {
		PaymentDTO newDto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id").buildAndExpand(newDto.getId()).toUri();
	
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<PaymentDTO> update(@PathVariable Long id, @RequestBody PaymentDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
}

