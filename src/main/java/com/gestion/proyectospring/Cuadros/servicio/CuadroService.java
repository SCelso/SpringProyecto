package com.gestion.proyectospring.Cuadros.servicio;

import java.util.List;

import com.gestion.proyectospring.Cuadros.entidades.Cuadro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CuadroService {

	public List<Cuadro> findAll();

	public Page<Cuadro> findAll(Pageable pageable);

	public void save(Cuadro cuadro);

	public Cuadro findOne(Long id);

	public void delete(Long id);
}
