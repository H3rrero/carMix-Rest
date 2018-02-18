package com.carmix.rest.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carmix.business.dto.Categoria;
import com.carmix.model.Usuario;

@RestController
@RequestMapping(value = "spotify")
public interface SpotifyRestService {

	@RequestMapping(value = "/categorias", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<Categoria>> getCategorias();

}
