package com.gestion.proyectospring.util.reportes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.gestion.proyectospring.Cuadros.entidades.Cuadro;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CuadroExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;

	private List<Cuadro> listaCuadros;

	public CuadroExporterExcel(List<Cuadro> listaCuadros) {
		this.listaCuadros = listaCuadros;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Cuadros");
	}

	private void escribirCabeceraDeTabla() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Autor");
		celda.setCellStyle(estilo);

		
		celda = fila.createCell(3);
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);
		

	}
	
	private void escribirDatosDeLaTabla() {
		int nueroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for(Cuadro cuadro : listaCuadros) {
			Row fila = hoja.createRow(nueroFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(cuadro.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(cuadro.getNombre());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(cuadro.getAutor());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			
			celda = fila.createCell(3);
			celda.setCellValue(cuadro.getFecha().toString());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			

		}
	}
	
	public void exportar(HttpServletResponse response) throws IOException {
		escribirCabeceraDeTabla();
		escribirDatosDeLaTabla();
		
		ServletOutputStream outPutStream = response.getOutputStream();
		libro.write(outPutStream);
		
		libro.close();
		outPutStream.close();
	}
}
