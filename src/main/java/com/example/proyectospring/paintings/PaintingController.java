package com.example.proyectospring.paintings;


import com.example.proyectospring.paintings.entities.Painting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PaintingController {
    private final PaintingService service;

    public PaintingController(PaintingService service) {
        this.service = service;
    }

    @GetMapping("/paintings")
    public String showPaintingList(Model model) {
        List<Painting> listPaintings = service.listAll();
        model.addAttribute("listPaintings", listPaintings);

        return "paintings";
    }

    @GetMapping("/paintings/new")
    public String showNewForm(Model model) {
        model.addAttribute("painting", new Painting());
        model.addAttribute("pageTitle", "Add New Painting");
        return "painting_form";
    }

    @PostMapping("/paintings/save")
    public String savePainting(Painting painting, RedirectAttributes ra) {
        service.save(painting);
        ra.addFlashAttribute("message", "The painting has been saved successfully.");
        return "redirect:/paintings";
    }

    @GetMapping("/paintings/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Painting painting = service.get(id);
            model.addAttribute("painting", painting);
            model.addAttribute("pageTitle", "Edit Painting (ID: " + id + ")");

            return "painting_form";
        } catch (Error e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/paintings";
        }
    }

    @GetMapping("/paintings/delete/{id}")
    public String deletePaintings(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The painting ID " + id + " has been deleted.");
        } catch (Error e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/paintings";
    }
}