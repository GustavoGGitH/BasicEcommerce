package com.GG.springboot.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.GG.springboot.ecommerce.entity.Order;
import com.GG.springboot.ecommerce.entity.OrderDetail;
import com.GG.springboot.ecommerce.entity.Producto;
import com.GG.springboot.ecommerce.service.IOrderDetailService;
import com.GG.springboot.ecommerce.service.IOrderService;
import com.GG.springboot.ecommerce.service.IProductoService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    /*
    @Autowired	
    private IUserService userService;*/

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderDetailService iOrderDetail;

   
    List<OrderDetail> detail = new ArrayList<OrderDetail>();
    Order order = new Order();
/*
	@GetMapping("")
	public String home(Model model,HttpSession session) {
		
		log.info("La Sesion del usuario en el getmapping del home : {} ", session.getAttribute("idusuario"));
		model.addAttribute("products", productService.findAll());
		
		//agrego sesion al model que le voy a pasar a la vista
		
		Integer idUsuario = (Integer) session.getAttribute("idusuario");
		model.addAttribute("session", idUsuario);
		
		log.info("Valor de 'idusuario' en la sesión estoy en el punto ante de redireccionar a user/home: {}", session.getAttribute("idusuario"));

		// List<Product> products = productService.findAll();

		// model.addAttribute("products", products);

		return "user/home";

	}*/
    @GetMapping("productohome/{id}")
    public String productHome(@PathVariable Long id, Model model) {
        log.info("Id Enviado como parámetro {}", id);
      
        Producto producto = productoService.findById(id);
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCar(@RequestParam Long id, @RequestParam float cantidad, Model model,HttpSession session
    		) {
        OrderDetail orderDetail = new OrderDetail();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Producto Producto = productoService.findById(id);

        log.info("Producto añadido {}", Producto);
        log.info("Cantidad {}", cantidad);
       

      

        orderDetail.setCantidad(cantidad);
        orderDetail.setPrecio(Producto.getPrecio());
     
        orderDetail.setNombre(Producto.getDesc_producto());
        orderDetail.setTotal(Producto.getPrecio() * cantidad);
        orderDetail.setProducto(Producto);

        Long idProducto = Producto.getId();
        boolean ingresado = detail.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

       if (!ingresado) {
            detail.add(orderDetail);
        }

        sumaTotal = detail.stream().mapToDouble(dt -> dt.calcularImporte()).sum();
        
        log.info("suma total del monto en orden {}", sumaTotal);
        order.setTotal(sumaTotal);

        model.addAttribute("cart", detail);
        model.addAttribute("order", order);

     //   model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Long id, Model model) {
        List<OrderDetail> ordenNueva = new ArrayList<OrderDetail>();

        for (OrderDetail detalleOrden : detail) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenNueva.add(detalleOrden);
            }
        }

        detail = ordenNueva;
        double sumaTotal = 0;
        sumaTotal = detail.stream().mapToDouble(dt -> dt.getTotal()).sum();
        order.setTotal(sumaTotal);

        model.addAttribute("cart", detail);
        model.addAttribute("order", order);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {
        model.addAttribute("cart", detail);
        model.addAttribute("order", order);
        model.addAttribute("session", session);
        return "user/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {

        
    	// Aquí tomo la session del usuario que es un objeto, lo paso a string y luego lo parseo a integer para fecién ahí si pasarlo como parámetro a findbyID
		// hasta no incluir sesión de usuario  renombre todo el if y solo dejo el return gg 21/01
    	/*if (session.getAttribute("idusuario")!=null) {
	    	User user = userService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
			log.info("Sesion del usuario : {} ", session.getAttribute("idusuario"));
	        model.addAttribute("cart", detail);
	        model.addAttribute("order", order);
	        model.addAttribute("user", user);
	        return "user/resumenorden";} 
    	else { 
    		return "user/resumenorden";
    	}*/
    
        model.addAttribute("cart", detail);
        model.addAttribute("order", order);
    
    	return "usuario/resumenorden";
    }	

	@GetMapping("/saveOrder")

	public String saveOrder(HttpSession session) {
		Date fechaCreacion = new Date();
		//Comento todo lo relacionado a usuario hasta que implemente el manejo de perfiles por usuario gg 21/01
		// Obtengo el usuario
	//	User user = userService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
	//	log.info("Estoy obteniendo el id de usuario en el método salvarorden : {} ", session.getAttribute("idusuario"));
		order.setFechadecreacion(fechaCreacion);
		order.setNumero(iOrderService.generarNumeroOrden());
		//order.setUsuario(user);
	//	log.info("Estoy salvando la orden con el id de usuario : {} ", session.getAttribute("idusuario"));
		iOrderService.saveOrder(order);

		// Guardamos detalle de la orden
		// Recorro todo el detalle y voy guardando la orden

		for (OrderDetail dt : detail) {

			dt.setOrden(order);

			iOrderDetail.saveOrderDetail(dt);

		}
		// Limpiar orden y lista

		order = new Order();

		detail.clear();

		return "redirect:/usuario";
	}

/*
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model) {
        List<Product> products = productService.findAll()
                .stream()
                .filter(p -> p.getNombre_articulo() != null && p.getNombre_articulo().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "user/home";
    }*/
}