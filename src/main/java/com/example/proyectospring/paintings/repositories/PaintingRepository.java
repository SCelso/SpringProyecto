package com.example.proyectospring.paintings.repositories;



import com.example.proyectospring.paintings.entities.Painting;
import org.springframework.data.repository.CrudRepository;

public interface PaintingRepository extends CrudRepository<Painting, Integer> {
    public Long countById(Integer id);
}