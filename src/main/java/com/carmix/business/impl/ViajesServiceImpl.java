package com.carmix.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carmix.business.api.ViajesService;
import com.carmix.business.dto.ViajeDto;
import com.carmix.dao.api.ViajesRepository;
import com.carmix.model.Viaje;

@Service
public class ViajesServiceImpl implements ViajesService{
	
	@Autowired
	private ViajesRepository viajesRepository;
	
//	@Autowired
//	private InfoRepository infoRepository;
	
	@Override
	public List<ViajeDto> getViajes() {
//		List<Viaje> viajes = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			Viaje v = new Viaje();
//			v.setId(new Long(i));
//			v.setOrigen("origen"+i);
//			v.setDestino("destino"+i);
//			viajes.add(v);
//		}
		List<Viaje> viajes = (List<Viaje>) viajesRepository.findAll();
		
		List<ViajeDto> dtos = new ArrayList<>();
		for (Viaje v : viajes) {
			ViajeDto dto = new ViajeDto();
			dto.setId(v.getId());
			dto.setOrigen(v.getOrigen());
			dto.setDestino(v.getDestino());
			dto.setDescripcion(v.getDescripcion());
			dto.setPlazas(v.getPlazas());
			dto.setPrecio(dto.getPrecio());
			dto.setUrl("/viajes/"+v.getId());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Viaje getViaje(Long id) {
		return viajesRepository.findOne(id);
		
	}

}
