package com.GG.springboot.ecommerce.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.GG.springboot.ecommerce.entity.Categoria;
import com.GG.springboot.ecommerce.entity.Producto;
import com.GG.springboot.ecommerce.service.CategoriaService;
import com.GG.springboot.ecommerce.service.IUploadFileService;
import com.GG.springboot.ecommerce.util.PageRender;

import jakarta.validation.Valid;

@Controller
@RequestMapping("")
//Permite mantener los atributos de la sesión durante todo el controlador , permite por ejemplo que al editar y luego llamar al método guardar
// no se pierdan los valores del id, así de esa manera distingue entre editar y salvar uno nuevo
@SessionAttributes("categoria")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	private IUploadFileService uploadFileService;

	SessionStatus HttpSession;
	
	
	@GetMapping(value = "/")
	public String home(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Categoria> categorias = categoriaService.TodaslasCategorias(pageRequest);

		PageRender<Categoria> pageRender = new PageRender<Categoria>("/", categorias);
		model.addAttribute("titulo", "Listado de Categorias");
		model.addAttribute("categorias", categorias);
		model.addAttribute("page", pageRender);
		model.addAttribute("entidadActual", "categorias");

		return "administrador/home";
	}
	
	@GetMapping(value = "/usuario")
	public String home_usuario(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Categoria> categorias = categoriaService.TodaslasCategorias(pageRequest);

		PageRender<Categoria> pageRender = new PageRender<Categoria>("/usuario", categorias);
		model.addAttribute("titulo", "Listado de Categorias");
		model.addAttribute("categorias", categorias);
		model.addAttribute("page", pageRender);
		model.addAttribute("entidadActual", "categorias");

		return "usuario/home";
	}
	
	
	@GetMapping(value = "/categorias")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Categoria> categorias = categoriaService.TodaslasCategorias(pageRequest);

		PageRender<Categoria> pageRender = new PageRender<Categoria>("/categorias", categorias);
		model.addAttribute("titulo", "Listado de Categorias");
		model.addAttribute("categorias", categorias);
		model.addAttribute("page", pageRender);
		model.addAttribute("entidadActual", "categorias");

		return "categorias/listcategorias";
	}

	// cuando le hacemos click en crear nueva categoría hace un get que carga
	// el formulario vació con un nuevo objeto categoría para llenar y dar el alta
	// con el id en null
	// que permitirá crear dicho registro con un id incremental

	@RequestMapping(value = "/categorias/form")
	public String crear(Map<String, Object> model) {

		Categoria categoria = new Categoria();
		model.put("categoria", categoria);
		model.put("titulo", "Formulario de Categoría de Productos");
		model.put("entidadActual", "categorias");
		return "categorias/categorias";
	}

	@RequestMapping(value = "/categorias/form", method = RequestMethod.POST)
	public String guardar(@Valid Categoria categoria, BindingResult result, Model model,

			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Categoría");
			return "categorias/categorias";
		}

		if (!foto.isEmpty()) {

			if (categoria.getId() != null && categoria.getId() > 0 && categoria.getFoto() != null
					&& categoria.getFoto().length() > 0) {

				uploadFileService.delete(categoria.getFoto());
			}

			String uniqueFilename = null;

			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			categoria.setFoto(uniqueFilename);
		}

		String mensajeFlash = (categoria.getId() != null) ? "Categoria editada con éxito!"
				: "Categoría creada con éxito!";

		categoriaService.save(categoria);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/categorias";

	}

	// método para eliminar una categoría por su id

	
	@RequestMapping(value = "/categorias/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

	    if (id > 0) {
	        Categoria categoria = categoriaService.findById(id);

	        List<Producto> productos = categoriaService.ListaProductos(id);

	        if (productos == null || productos.isEmpty()) {
	            categoriaService.delete(id);
	            flash.addFlashAttribute("success", "Categoría eliminada con éxito!");

	            if (categoria.getFoto() != null) {
	                if (uploadFileService.delete(categoria.getFoto())) {
	                    flash.addFlashAttribute("info", "Foto " + categoria.getFoto() + " eliminada con éxito!");
	                } else {
	                    flash.addFlashAttribute("info", "Foto no encontrada");
	                }
	            }
	        } else {
	            flash.addFlashAttribute("error", "La categoría no puede ser eliminada porque contiene artículos");
	        }
	    }

	    return "redirect:/categorias";
	}

	// metodo para editar una categoria en particular

	@RequestMapping(value = "/categorias/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Categoria categoria = null;

		if (id > 0) {
			categoria = categoriaService.findById(id);
			if (categoria == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/categorias";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/categorias";
		}

		model.put("categoria", categoria);
		model.put("titulo", "Editar Categoria");
		model.put("entidadActual", "categorias");
		return "categorias/categorias";
	}

	// método para ver una categoría
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Categoria categoria = categoriaService.findById(id);
		if (categoria == null) {
			flash.addFlashAttribute("error", "La categoría no existe en la base de datos");
			return "redirect:/categorias";
		}

		model.put("categoria", categoria);
		model.put("titulo", "Detalle de categoría :  " + categoria);
		model.put("entidadActual", "categorias");
		return "categorias/ver";
	}
	
	// método para ver una categoría como usuario
		@GetMapping(value = "/usuario/ver/{id}")
		public String vercomousuario(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

			Categoria categoria = categoriaService.findById(id);
			if (categoria == null) {
				flash.addFlashAttribute("error", "La categoría no existe en la base de datos");
				return "redirect:/categorias";
			}

			model.put("categoria", categoria);
			model.put("titulo", "Vista Usuario - Detalle de categoría :  " + categoria);
			model.put("entidadActual", "categorias");
			return "usuario/ver";
		}

	// Obtiene la foto del recurso especificado
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

}
