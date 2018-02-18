package com.carmix.business.api;

import java.util.List;

import com.carmix.business.dto.ViajeDto;
import com.carmix.model.Viaje;


public interface ViajesService {
	
	public List<ViajeDto> getViajes();

	public ViajeDto getViaje(Long id);

	public ViajeDto crearViaje(ViajeDto dto);

	public List<String> getDestinos();

	public ViajeDto actualizarViaje(ViajeDto dto);

	public Viaje eliminarViaje(Long id);

	public byte[] getViajePDF(Long id);
}
