package com.GG.springboot.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GG.springboot.ecommerce.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Long>{

}
