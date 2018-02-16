package com.carmix.rest.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carmix.business.dto.UserTokenDto;
import com.carmix.model.Usuario;

@RestController
@RequestMapping(value = "user")
public interface UserRestService {

	@RequestMapping(value = "/logIn", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserTokenDto> logIn(Usuario usuario);

}
