package com.GG.springboot.ecommerce.service;

import org.springframework.stereotype.Service;

import com.GG.springboot.ecommerce.entity.OrderDetail;




@Service
public interface IOrderDetailService {
	
	//Solamente declaro el método , en este caso es saveOrderDetail que permite guardar la entidad Order Detail
	OrderDetail saveOrderDetail(OrderDetail orderDetail);

}
