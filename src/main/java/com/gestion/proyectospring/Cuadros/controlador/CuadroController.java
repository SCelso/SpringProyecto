package com.gestion.proyectospring.Cuadros.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.gestion.proyectospring.Cuadros.entidades.Cuadro;
import com.gestion.proyectospring.Cuadros.servicio.CuadroService;
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

import com.gestion.proyectospring.util.paginacion.PageRender;
import com.gestion.proyectospring.util.reportes.CuadroExporterExcel;
import com.gestion.proyectospring.util.reportes.CuadroExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class CuadroController {

	@Autowired
	private CuadroService cuadroService;
	
	@GetMapping("/ver/{id}")
	public String verDetallesDelCuadro(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash) {
		Cuadro cuadro = cuadroService.findOne(id);
		if(cuadro == null) {
			flash.addFlashAttribute("error", "El cuadro no existe en la base de datos");
			return "redirect:/listar";
		}
		
		modelo.put("cuadro", cuadro);
		modelo.put("titulo", "Detalles del cuadro " + cuadro.getNombre());
		return "ver";
	}
	
	@GetMapping({"/","/listar",""})
	public String listarCuadros(@RequestParam(name = "page",defaultValue = "0") int page, Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Cuadro> cuadros = cuadroService.findAll(pageRequest);
		PageRender<Cuadro> pageRender = new PageRender<>("/listar", cuadros);
		
		modelo.addAttribute("titulo","Listado de cuadros");
		modelo.addAttribute("cuadros",cuadros);
		modelo.addAttribute("page", pageRender);
		
		return "listar";
	}
	
	@GetMapping("/form")
	public String mostrarFormularioDeRegistrarCuadro(Map<String,Object> modelo) {
		Cuadro cuadro = new Cuadro();
		modelo.put("cuadro", cuadro);
		modelo.put("titulo", "Registro de cuadros");
		return "form";
	}
	
	@PostMapping("/form")
	public String guardarCuadro(@Valid Cuadro cuadro, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
			modelo.addAttribute("titulo", "Registro de cuadro");
			return "form";
		}
		
		String mensaje = (cuadro.getId() != null) ? "El cuadro ha sido editato con exito" : "Cuadro registrado con exito";
		
		cuadroService.save(cuadro);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarCuadro(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Cuadro cuadro = null;
		if(id > 0) {
			cuadro = cuadroService.findOne(id);
			if(cuadro == null) {
				flash.addFlashAttribute("error", "El ID del cuadro no existe en la base de datos");
				return "redirect:/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del cuadro no puede ser cero");
			return "redirect:/listar";
		}
		
		modelo.put("cuadro", cuadro);
		modelo.put("titulo", "Edición de cuadro");
		return "form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCuadro(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if(id > 0) {
			cuadroService.delete(id);
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
		
		List<Cuadro> cuadros = cuadroService.findAll();
		
		CuadroExporterPDF exporter = new CuadroExporterPDF(cuadros);
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
		
		List<Cuadro> cuadros = cuadroService.findAll();
		
		CuadroExporterExcel exporter = new CuadroExporterExcel(cuadros);
		exporter.exportar(response);
	}
}
