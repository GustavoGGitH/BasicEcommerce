package com.GG.springboot.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GG.springboot.ecommerce.entity.OrderDetail;



@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {

}
