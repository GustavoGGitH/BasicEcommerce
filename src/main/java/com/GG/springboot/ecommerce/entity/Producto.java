package com.GG.springboot.ecommerce.entity;

import java.io.Serializable;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "producto")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotEmpty
	private String desc_producto;

	@Column
	private String foto;
	
	@Column
	public float precio;

	
	/*@Column
	
	private Integer categoria_id;*/
	
	// Acá indicamos que muchos(clase en la que estamos) Productos una categoría
		//  un producto una sola categoría
		// Lazy es para evitar que traiga todos los registros de una, los va gestionando
		// a medida que los ncecesitamos
		@ManyToOne(fetch = FetchType.LAZY)
		private Categoria categoria;

	public Producto() {
		super();
	}


	public Producto(Long id, @NotEmpty String desc_producto, String foto, float precio, Categoria categoria) {
		super();
		this.id = id;
		this.desc_producto = desc_producto;
		this.foto = foto;
		this.precio = precio;
		this.categoria = categoria;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesc_producto() {
		return desc_producto;
	}

	public void setDesc_producto(String desc_producto) {
		this.desc_producto = desc_producto;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}



	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return  desc_producto;
	}


	private static final long serialVersionUID = 1L;

}
