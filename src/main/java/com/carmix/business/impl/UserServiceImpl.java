package com.carmix.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carmix.business.api.UserService;
import com.carmix.business.dto.ViajeDto;
import com.carmix.dao.api.UserRepository;
import com.carmix.model.Usuario;
import com.carmix.model.Viaje;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;

	@Override
	public Usuario logIn(Usuario usuario) {
		Usuario usuarioDB = ur.logIn(usuario.getUser(), usuario.getPassword());
		return usuarioDB;
	}

	@Override
	public Usuario getUser(Long id) {
		return this.ur.findOne(id);
	}

	@Override
	public List<ViajeDto> findViajesUsuario(Long id) {
		Usuario u = this.getUser(id);
		if(u != null){
			List<Viaje> viajes = (List<Viaje>) ur.findViajesPorusuario(id);
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
		return null;
	}

}
