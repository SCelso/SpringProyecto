package com.example.proyectospring.paintings.services;

import java.util.List;

import com.example.proyectospring.paintings.entities.Painting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaintingService {

    public List<Painting> findAll();

    public Page<Painting> findAll(Pageable pageable);

    public void save(Painting painting);

    public Painting findOne(Long id);

    public void delete(Long id);
}
