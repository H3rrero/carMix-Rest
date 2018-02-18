package com.carmix.business.api;

import java.util.List;

import com.carmix.business.dto.ViajeDto;


public interface ViajesService {
	
	public List<ViajeDto> getViajes();

	public ViajeDto getViaje(Long id);

	public ViajeDto crearViaje(ViajeDto dto);

	public List<String> getDestinos();

	public ViajeDto actualizarViaje(ViajeDto dto);

	public void eliminarViaje(Long id);
}
