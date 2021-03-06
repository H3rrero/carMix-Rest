package com.carmix.rest.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carmix.business.dto.ViajeDto;

@RestController
@RequestMapping(value = "viajes")
public interface ViajesRestService {

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ViajeDto>> getViajes();

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ViajeDto> crearViaje(ViajeDto dto);
	
	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> actualizar(ViajeDto dto);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ViajeDto> getViaje(Long id);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ViajeDto> eliminarViaje(Long id);
	
	@RequestMapping(value = "/{id}/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<byte[]> getViajePDF(Long id);
	
	@RequestMapping(value = "/destinos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<String>> getDestino();
	

}
