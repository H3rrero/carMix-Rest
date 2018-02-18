package com.carmix.business.api;

import java.util.List;

import com.carmix.business.dto.UserTokenDto;
import com.carmix.business.dto.ViajeDto;
import com.carmix.model.Usuario;

public interface UserService {

	public Usuario logIn(Usuario usuario);

	public Usuario getUser(Long id);

	public List<ViajeDto> findViajesUsuario(Long id);

	public UserTokenDto register(Usuario usuario);

}
