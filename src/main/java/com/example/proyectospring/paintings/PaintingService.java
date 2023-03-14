package com.example.proyectospring.paintings;

import com.example.proyectospring.paintings.entities.Painting;
import com.example.proyectospring.paintings.repositories.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaintingService {
    private final PaintingRepository repo;

    public PaintingService(PaintingRepository repo) {
        this.repo = repo;
    }

    public List<Painting> listAll() {
        return (List<Painting>) repo.findAll();
    }

    public void save(Painting painting) {
        repo.save(painting);
    }

    public Painting get(Integer id) throws Error {
        Optional<Painting> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new Error("Could not find any paintings with ID " + id);
    }

    public void delete(Integer id) throws Error{
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new Error("Could not find any paintings with ID " + id);
        }
        repo.deleteById(id);
    }
}