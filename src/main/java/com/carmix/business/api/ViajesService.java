package com.carmix.business.api;

import java.util.List;

import com.carmix.business.dto.ViajeDto;
import com.carmix.model.Viaje;


public interface ViajesService {
	
	public List<ViajeDto> getViajes();

	public Viaje getViaje(Long id);
}
