package com.example.proyectospring.paintings.repositories;



import com.example.proyectospring.paintings.entities.Painting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaintingRepository extends PagingAndSortingRepository<Painting, Long> ,CrudRepository<Painting,Long> {
}