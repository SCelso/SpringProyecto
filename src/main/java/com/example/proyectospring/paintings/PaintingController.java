package com.example.proyectospring.paintings;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



import com.example.proyectospring.paintings.entities.Painting;
import com.example.proyectospring.paintings.pagination.PageRender;
import com.example.proyectospring.paintings.services.PaintingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.lowagie.text.DocumentException;

@Controller
public class PaintingController {

    @Autowired
    private PaintingService paintingService;

    @GetMapping("/ver/{id}")
    public String verDetallesDelCuadro(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
        Painting painting = paintingService.findOne(id);
        if(painting == null) {
            flash.addFlashAttribute("error", "El cuadro no existe en la base de datos");
            return "redirect:/listar";
        }

        modelo.put("cuadro",painting);
        modelo.put("titulo", "Detalles del cuadro " + painting.getNombre());
        return "ver";
    }

    @GetMapping({"/","/listar",""})
    public String listarCuadros(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Painting> paintings = paintingService.findAll(pageRequest);
        PageRender<Painting> pageRender = new PageRender<>("/listar", paintings);

        modelo.addAttribute("titulo","Listado de cuadros");
        modelo.addAttribute("cuadros",paintings);
        modelo.addAttribute("page", pageRender);

        return "listar";
    }

    @GetMapping("/form")
    public String mostrarFormularioDeRegistrarCliente(Map<String,Object> modelo) {
        Painting painting = new Painting();
        modelo.put("cuadro", painting);
        modelo.put("titulo", "Registro de cuadros");
        return "form";
    }

    @PostMapping("/form")
    public String guardarCuadro(@Valid Painting painting, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        if(result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de cuadro");
            return "form";
        }

        String mensaje = (painting.getId() != null) ? "El cuadro ha sido editato con exito" : "Cuadro registrado con exito";

        paintingService.save(painting);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listar";
    }

    @GetMapping("/form/{id}")
    public String editarCuadro(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
        Painting painting = null;
        if(id > 0) {
            painting = paintingService.findOne(id);
            if(painting == null) {
                flash.addFlashAttribute("error", "El ID del cuadro no existe en la base de datos");
                return "redirect:/listar";
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID del cuadro no puede ser cero");
            return "redirect:/listar";
        }

        modelo.put("cuadro",painting);
        modelo.put("titulo", "Edición de cuadro");
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCuadro(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
        if(id > 0) {
            paintingService.delete(id);
            flash.addFlashAttribute("success", "Cuadro eliminado con exito");
        }
        return "redirect:/listar";
    }

    @GetMapping("/exportarPDF")
    public void exportarListadoDeCuadrosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Cuadros_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Painting> paintings = paintingService.findAll();

        PaintingExporterPDF exporter = new PaintingExporterPDF(paintings);
        exporter.exportar(response);
    }

    @GetMapping("/exportarExcel")
    public void exportarListadoDeCuadrosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Cuadros_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Painting> paintings = paintingService.findAll();

        PaintingExporterExcel exporter = new PaintingExporterExcel(paintings);
        exporter.exportar(response);
    }
}
