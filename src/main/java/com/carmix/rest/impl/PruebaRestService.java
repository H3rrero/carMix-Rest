package com.carmix.rest.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "prueba")
public interface PruebaRestService {
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<String> getPrueba(Long id);

}
