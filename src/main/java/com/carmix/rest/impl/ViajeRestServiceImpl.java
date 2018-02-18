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
import com.carmix.model.Viaje;
import com.carmix.rest.api.ViajesRestService;

@Component
public class ViajeRestServiceImpl implements ViajesRestService {

	@Autowired
	private ViajesService vs;

	@Override
	public ResponseEntity<List<ViajeDto>> getViajes() {

		List<ViajeDto> viajes = vs.getViajes();
		if (viajes == null) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(viajes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ViajeDto> getViaje(@PathVariable Long id) {
		ViajeDto v = vs.getViaje(id);
		if (v == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(v, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ViajeDto> crearViaje(@RequestBody ViajeDto dto) {
		ViajeDto v = new ViajeDto();
		try {
			v = vs.crearViaje(dto);
			if (v == null) {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(v, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<String>> getDestino() {
		List<String> destinos = vs.getDestinos();
		if (destinos == null) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(destinos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> actualizar(@RequestBody ViajeDto dto) {
		ViajeDto response = vs.actualizarViaje(dto);
		if (response == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ViajeDto> eliminarViaje(@PathVariable Long id) {
		Viaje v = vs.eliminarViaje(id);
		if (v == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}

	@Override
	public ResponseEntity<byte[]> getViajePDF(@PathVariable Long id) {
		byte[] pdf = vs.getViajePDF(id);
		if(pdf == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<byte[]>(pdf, HttpStatus.OK);
	}

}
