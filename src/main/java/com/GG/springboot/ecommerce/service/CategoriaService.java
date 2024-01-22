package com.GG.springboot.ecommerce.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.ecommerce.entity.Categoria;
import com.GG.springboot.ecommerce.entity.Producto;
import com.GG.springboot.ecommerce.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService implements ICategoria {
	
	@Autowired
	public CategoriaRepository categoriarepository;



	
	@Override
	@Transactional
	public Page<Categoria> TodaslasCategorias(Pageable pageable) {
		// TODO Auto-generated method stub
		return categoriarepository.findAll(pageable);
	}


	@Override
	@Transactional
	public void save(Categoria categoria) {
		 categoriarepository.save(categoria);
		
	}

	@Override
	@Transactional
	public Categoria findById(Long id) {
		// TODO Auto-generated method stub
		return categoriarepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional()
	public void delete(Long id) {
		categoriarepository.deleteById(id);
	}


	@Override
	public List<Categoria> ListaCategorias() {
		// TODO Auto-generated method stub
		return categoriarepository.findAll();
	}


	@Override
	public List<Producto> ListaProductos(Long id) {
		// TODO Auto-generated method stub
		return categoriarepository.findProductosByIdCateg(id);
	}

	
	
	
}
