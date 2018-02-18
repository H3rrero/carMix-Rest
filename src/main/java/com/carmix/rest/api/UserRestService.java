package com.carmix.rest.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carmix.business.dto.UserTokenDto;
import com.carmix.business.dto.ViajeDto;
import com.carmix.model.Usuario;

@RestController
@RequestMapping(value = "user")
public interface UserRestService {

	@RequestMapping(value = "/logIn", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserTokenDto> logIn(Usuario usuario);
	
	@RequestMapping(value = "/", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserTokenDto> register(Usuario usuario);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Usuario> getUsuario(Long id);
	
	@RequestMapping(value = "/{id}/viajes", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ViajeDto>> getViajesUsuario(Long id);

}
