package com.GG.springboot.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.GG.springboot.ecommerce.entity.Categoria;
import com.GG.springboot.ecommerce.entity.Producto;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	// Esta es personalizada
	@Query("select p from Producto p where p.categoria.id = ?1")
	public List<Producto> findProductosByIdCateg(Long categoriaId);
}
