package com.carmix.dao.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carmix.model.UserViaje;
import com.carmix.model.UserViajeId;

@Repository
public interface UserViajeRepository extends CrudRepository<UserViaje, UserViajeId>{
	
}