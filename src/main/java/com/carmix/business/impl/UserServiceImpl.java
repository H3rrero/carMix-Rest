package com.carmix.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.carmix.business.api.UserService;
import com.carmix.dao.api.UserRepository;
import com.carmix.model.Usuario;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;

	@Override
	public Usuario logIn(Usuario usuario) {
		Usuario usuarioDB = ur.logIn(usuario.getUser(), usuario.getPassword());
		return usuarioDB;
	}

}
