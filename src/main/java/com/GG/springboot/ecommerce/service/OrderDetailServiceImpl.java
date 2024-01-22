package com.GG.springboot.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GG.springboot.ecommerce.entity.OrderDetail;
import com.GG.springboot.ecommerce.repository.IOrderDetailRepository;





@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
	
	@Autowired
	private IOrderDetailRepository OrderDetailRepository;

	@Override
	public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		return OrderDetailRepository.save(orderDetail);
	}
	
	

}
