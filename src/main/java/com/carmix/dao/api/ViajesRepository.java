package com.carmix.dao.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carmix.model.Viaje;

@Repository
public interface ViajesRepository extends CrudRepository<Viaje, Long>{

}
