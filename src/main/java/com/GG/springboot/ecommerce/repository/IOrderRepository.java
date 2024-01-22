package com.GG.springboot.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.ecommerce.entity.Order;



@Repository
public interface IOrderRepository extends JpaRepository <Order, Long>{
	
	//List<Order> findByUsuario(User user);

}
