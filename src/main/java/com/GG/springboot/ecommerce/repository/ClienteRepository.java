package com.GG.springboot.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.ecommerce.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{
	
	
}
	