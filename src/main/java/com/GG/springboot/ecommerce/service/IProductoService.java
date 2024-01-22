package com.GG.springboot.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.GG.springboot.ecommerce.entity.Producto;

public interface IProductoService {
	
	public Producto findById(Long id);

	public void save(Producto producto);

	public void delete(Long id);

	public Page<Producto> TodosLosArticulos(Pageable pageable);

}
