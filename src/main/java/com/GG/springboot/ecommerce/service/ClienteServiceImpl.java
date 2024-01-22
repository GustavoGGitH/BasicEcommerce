package com.GG.springboot.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GG.springboot.ecommerce.entity.Cliente;
import com.GG.springboot.ecommerce.entity.Producto;
import com.GG.springboot.ecommerce.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepository iClienteDao;

	@Override
	@Transactional()
	public List<Cliente> findAll() {
		// como es un iterable hago un cast de iterable a list en el return
		return (List<Cliente>) iClienteDao.findAll();
	}

	@Override
	@Transactional()
	public void save(Cliente cliente) {
		iClienteDao.save(cliente);

	}

	@Override
	@Transactional()
	public Cliente findOne(Long id) {
		// como retorna un optional le agrego al findby el or else
		return iClienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional()
	public void eliminar(Long id) {
		iClienteDao.deleteById(id);
	}

	@Override
	@Transactional()
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return iClienteDao.findAll(pageable);
	}

}