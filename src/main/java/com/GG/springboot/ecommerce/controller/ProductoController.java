package com.GG.springboot.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.GG.springboot.ecommerce.service.ICategoria;
import com.GG.springboot.ecommerce.service.IProductoService;
import com.GG.springboot.ecommerce.service.IUploadFileService;
import com.GG.springboot.ecommerce.util.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("producto")
public class ProductoController {

	@Autowired
	private IProductoService iProductoService;
	
	@Autowired
	
	private ICategoria iCategoriaService;
	
	
	@Autowired
	private IUploadFileService uploadFileService;

	SessionStatus HttpSession;
	
	@GetMapping(value = "/productos")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);
		
		Page<Producto> productos = iProductoService.TodosLosArticulos(pageRequest);


		PageRender<Producto> pageRender = new PageRender<Producto>("/productos", productos);
		model.addAttribute("titulo", "Listado de Productos");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		model.addAttribute("entidadActual", "productos");

		return "productos/listproductos";
	}
	
	// cuando le hacemos click en crear nuevo artículo hace un get que carga 
	// el formulario vació con un nuevo objeto artículo para llenar y dar el alta con el id en null
	// que permitirá crear dicho registro con un id incremental

	@RequestMapping(value = "/productos/form")
	public String crear(Map<String, Object> model) {

		Producto producto = new Producto();
		model.put("producto", producto);
		model.put("titulo", "Formulario de Productos");
		model.put("entidadActual", "productos");
	    List<Categoria> categorias = iCategoriaService.ListaCategorias(); // Debes implementar este método
	    model.put("categorias", categorias);
		return "productos/productos";
	}
	
	// método para guardar el producto
	@RequestMapping(value = "/productos/form", method = RequestMethod.POST)
	public String guardar(@Valid Producto producto, BindingResult result, Model model,

			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Producto");
			return "productos/productos";
		}

		if (!foto.isEmpty()) {

			if (producto.getId() != null && producto.getId() > 0 && producto.getFoto() != null
					&& producto.getFoto().length() > 0) {

				uploadFileService.delete(producto.getFoto());
			}

			String uniqueFilename = null;

			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			producto.setFoto(uniqueFilename);
		}

		String mensajeFlash = (producto.getId() != null) ? "Producto editado con éxito!"
				: "Producto creado con éxito!";

		iProductoService.save(producto);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/productos";

	}

	// metodo para editar un producto en particular

	@RequestMapping(value = "/productos/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Producto producto = null;

		if (id > 0) {
			producto = iProductoService.findById(id);
			if (producto== null) {
				flash.addFlashAttribute("error", "El ID del producto no existe en la BBDD!");
				return "redirect:/productos";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del producto no puede ser cero!");
			return "redirect:/productos";
		}
		List<Categoria> categorias = iCategoriaService.ListaCategorias();

		model.put("producto", producto);
		model.put("titulo", "Editar Producto");
		model.put("entidadActual", "productos");
		model.put("categorias", categorias);
		return "productos/productos";
	}
	
	// método para eliminar una categoría por su id
		@RequestMapping(value = "/productos/eliminar/{id}")
		public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

			if (id > 0) {
				Producto producto = iProductoService.findById(id);

				iProductoService.delete(id);
				flash.addFlashAttribute("success", "Producto Eliminado con éxito!");
				if (producto.getFoto() != null) {

					if (uploadFileService.delete(producto.getFoto())) {
						flash.addFlashAttribute("info", "Foto " + producto.getFoto() + " eliminada con exito!");
					} else {

						flash.addFlashAttribute("info", "Foto no encontrada");

					}
				}
			}

			return "redirect:/productos";
		}

	
	//método para ver un producto
	@GetMapping(value = "productos/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Producto producto = iProductoService.findById(id);
		if (producto == null) {
			flash.addFlashAttribute("error", "El Producto no existe en la base de datos");
			return "redirect:/productos";
		}

		model.put("producto", producto);
		model.put("titulo", "Detalle de producto  :  " + producto);
		model.put("entidadActual", "productos");
		return "productos/ver";
	}
	
	
	
/*	// Obtiene la foto del recurso especificado
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFotoProducto(@PathVariable String filename) {

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
	}*/

}
