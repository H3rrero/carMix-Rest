package com.carmix.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.carmix.business.api.ViajesService;
import com.carmix.business.dto.ViajeDto;
import com.carmix.rest.api.ViajesRestService;

@Component
public class ViajeRestServiceImpl implements ViajesRestService{

	@Autowired
	private ViajesService vs;
	
	@Override
	public ResponseEntity<List<ViajeDto>> getViajes() {
		
		List<ViajeDto> viajes = vs.getViajes();
		return new ResponseEntity<>(viajes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ViajeDto> getViaje(@PathVariable Long id) {
		ViajeDto v = vs.getViaje(id);
		return new ResponseEntity<>(v, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ViajeDto> crearViaje(@RequestBody ViajeDto dto) {
		ViajeDto v = vs.crearViaje(dto);
		return new ResponseEntity<>(v, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<String>> getDestino() {
		List<String> destinos = vs.getDestinos();
		return new ResponseEntity<>(destinos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> actualizar(@RequestBody ViajeDto dto) {
		ViajeDto response = vs.actualizarViaje(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
