package com.carmix.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carmix.business.api.UserService;
import com.carmix.business.dto.UserTokenDto;
import com.carmix.business.dto.ViajeDto;
import com.carmix.dao.api.UserRepository;
import com.carmix.dao.api.ViajesRepository;
import com.carmix.model.Usuario;
import com.carmix.model.Viaje;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;
	
	@Autowired
	ViajesRepository vr;

	@Override
	public Usuario logIn(Usuario usuario) {
		List<Usuario> usuariosDB = ur.logIn(usuario.getUser(), usuario.getPassword());
		if(usuariosDB == null || usuariosDB.isEmpty()){
			return null;
		}
		return usuariosDB.get(0);
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
				dto.setPrecio(v.getPrecio());
				
				dto.setCreador(id);
				dto.setUrl("/viajes/"+v.getId());
				dtos.add(dto);
			}
			return dtos;
		}
		return null;
	}

	@Override
	public UserTokenDto register(Usuario usuario) {
		Usuario u = this.ur.save(usuario);
		UserTokenDto dto = new UserTokenDto();
		
		dto.setId(u.getId());
		dto.setToken("tokenCarMix");
		dto.setUser(u.getUser());
		
		return dto;
	}

}
