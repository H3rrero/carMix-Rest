package com.carmix.rest.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.carmix.model.Viaje;
import com.carmix.rest.api.ViajesRestService;

@Component
public class ViajeRestServiceImpl implements ViajesRestService{

	@Override
	public ResponseEntity<List<Viaje>> getViajes() {
		
		List<Viaje> viajes = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Viaje v = new Viaje();
			v.setId(new Long(i));
			v.setOrigen("origen"+i);
			v.setDestino("destino"+i);
			viajes.add(v);
		}
		return new ResponseEntity<>(viajes, HttpStatus.OK);
	}

}
