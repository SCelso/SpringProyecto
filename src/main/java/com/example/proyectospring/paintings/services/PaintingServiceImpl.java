package com.example.proyectospring.paintings.services;

import com.example.proyectospring.paintings.entities.Painting;
import com.example.proyectospring.paintings.repositories.PaintingRepository;
import com.example.proyectospring.paintings.services.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaintingServiceImpl implements PaintingService {
    @Autowired
    private PaintingRepository paintingRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Painting> findAll() {
        return (List<Painting>) paintingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Painting> findAll(Pageable pageable) {
        return paintingRepository.findAll(pageable);
    }



    @Override
    @Transactional
    public void save(Painting painting) {
        paintingRepository.save(painting);
    }


    @Override
    @Transactional(readOnly = true)
    public Painting findOne(Long id) {
        return paintingRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        paintingRepository.deleteById(id);

    }

}