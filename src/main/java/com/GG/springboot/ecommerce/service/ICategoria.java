package com.GG.springboot.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.GG.springboot.ecommerce.entity.Categoria;
import com.GG.springboot.ecommerce.entity.Producto;

public interface ICategoria {

	public Categoria findById(Long id);

	public void save(Categoria categoria);

	public void delete(Long id);

	public Page<Categoria> TodaslasCategorias(Pageable pageable);
	
	public List<Categoria> ListaCategorias();
	
	public List<Producto> ListaProductos(Long id);

}
