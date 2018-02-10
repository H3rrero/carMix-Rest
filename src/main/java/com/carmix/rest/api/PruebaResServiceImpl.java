package com.carmix.rest.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.carmix.rest.impl.PruebaRestService;

@Component
public class PruebaResServiceImpl implements PruebaRestService{

	@Override
	public ResponseEntity<String> getPrueba(@PathVariable Long id) {
		
		return new ResponseEntity<>("Hola: " +id.toString(), HttpStatus.OK);
	}

}
