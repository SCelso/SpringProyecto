package com.gestion.proyectospring.Cuadros.servicio;

import java.util.List;

import com.gestion.proyectospring.Cuadros.entidades.Cuadro;
import com.gestion.proyectospring.Cuadros.repositorios.CuadroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CuadroServiceImpl implements CuadroService {

	@Autowired
	private CuadroRepository cuadroRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cuadro> findAll() {
		return (List<Cuadro>) cuadroRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cuadro> findAll(Pageable pageable) {
		return cuadroRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Cuadro cuadro) {
		cuadroRepository.save(cuadro);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cuadroRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cuadro findOne(Long id) {
		return cuadroRepository.findById(id).orElse(null);
	}
	
}
