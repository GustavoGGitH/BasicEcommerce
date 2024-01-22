package com.GG.springboot.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.ecommerce.entity.Producto;
import com.GG.springboot.ecommerce.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	ProductoRepository productoRepository;

	@Override
	@Transactional
	public Producto findById(Long id) {

		return productoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Producto producto) {
		productoRepository.save(producto);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoRepository.deleteById(id);

	}

	@Override
	@Transactional
	public Page<Producto> TodosLosArticulos(Pageable pageable) {

		return productoRepository.findAll(pageable);
	}

}
