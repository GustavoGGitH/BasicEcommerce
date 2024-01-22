package com.GG.springboot.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.GG.springboot.ecommerce.entity.Cliente;



public interface IClienteService {

	public List<Cliente> findAll();
	
	public void  save(Cliente cliente);
	
	public Cliente findOne(Long id);

	public void  eliminar(Long id);
	
	public Page<Cliente> findAll(Pageable pageable);


	

}
