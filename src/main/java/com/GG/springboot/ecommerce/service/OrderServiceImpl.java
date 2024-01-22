package com.GG.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GG.springboot.ecommerce.entity.Order;
import com.GG.springboot.ecommerce.repository.IOrderRepository;


@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository orderRepository;

	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}

	@Override
	public List<Order> findAllOrder() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	// 0000010
	public String generarNumeroOrden() {
	    List<Order> ordenes = findAllOrder();
	    int numero = ordenes.isEmpty() ? 1 : ordenes.stream().mapToInt(o -> Integer.parseInt(o.getNumero())).max().orElse(0) + 1;

	    String numeroConcatenado = String.format("%08d", numero);
	    return numeroConcatenado;
	} 	

	/*@Override
	public List<Order> findByUser(User user) {
		// TODO Auto-generated method stub
		return orderRepository.findByUsuario(user);
	}*/

	@Override
	public Optional<Order> findById(Long id)  {
		// TODO Auto-generated method stub
		return orderRepository.findById(id);
	}


	
	

}
