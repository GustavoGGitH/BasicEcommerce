package com.GG.springboot.ecommerce.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotEmpty
	private String desc_categoria;
	
	@Column
	//@NotNull
	public String foto;

	//Acá indicamos la relación un Categoría, muchos productos
		//Lazy logramos que no se carguen todos los artículos de una sola vez sin usarlas, las vamos invocando con el get
		//a medida que se necesitan y por tanto no gastamos recursos
		//CascadeType en All permite por ejemplo que si se elimina una categoría se eliminen todas sus facturas
		
		//en mappedBy va el atributo de la otra clase con la cual está relacionada en este caso Cliente de la clase Facturas
		//el mappedBy lo hace bidireccional Categoria lista de productos, producto una categoría
		//De forma automática va a crear en la tabla productos un id_categoría
		@OneToMany(mappedBy="categoria",fetch=FetchType.LAZY, cascade=CascadeType.ALL)

		private List<Producto> productos;

		// acá genero el constructor

	public Categoria() {
			this.productos = new ArrayList<Producto>();
		}


	public Categoria(Long id, String desc_categoria,String foto) {
		super();
		this.id = id;
		this.desc_categoria = desc_categoria;
		this.foto= foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesc_categoria() {
		return desc_categoria;
	}

	public void setDesc_categoria(String desc_categoria) {
		this.desc_categoria = desc_categoria;
	}

	
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	

	public List<Producto> getProductos() {
		return productos;
	}


	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}


	@Override
	public String toString() {
		return   desc_categoria ;
	}

	private static final long serialVersionUID = 1L;

}
