package com.GG.springboot.ecommerce.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detail_order")
public class OrderDetail implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	private Float cantidad;

	private Float precio;

	private Float total;

	@ManyToOne(fetch = FetchType.LAZY)
	// como estamos mapeando por producto va a crear por debajo el atributo
	// producto_id para relacionarlo con producto pero lo podemo especificar
	
	@JoinColumn(name = "producto_id")

	private Producto producto;

	// no es necesario pero lo creamos de form explícita de forma tal que quede
	// claro, si se omite toma para la relación la creación de un nuevo campo
	// nombre de la clase sub guion id
	


	@ManyToOne
/*	@JoinColumn(name = "orden_id", insertable = false, updatable = false) */
	private Order orden;
	
	

	// método que calcula el importe total, falta agregar importe
	// lo comento hasta que decida de donde voy a tomar el precio GG 17/01/2024

	public Double calcularImporte() {

		return cantidad.doubleValue() * producto.getPrecio();
	}

	public OrderDetail() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Order getOrden() {
		return orden;
	}

	public void setOrden(Order orden) {
		this.orden = orden;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "DetailOrder [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", total=" + total
				+ ", producto=" + producto + ", orden=" + orden + "]";
	}

	private static final long serialVersionUID = 1L;

}
