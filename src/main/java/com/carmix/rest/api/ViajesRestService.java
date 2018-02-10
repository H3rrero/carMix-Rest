package com.carmix.rest.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carmix.model.Viaje;

@RestController
@RequestMapping(value = "viaje")
public interface ViajesRestService {

	@RequestMapping(value = "/", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<Viaje>> getViajes();

}
