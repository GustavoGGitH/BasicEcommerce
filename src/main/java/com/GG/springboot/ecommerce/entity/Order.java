package com.GG.springboot.ecommerce.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "ordenes")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String numero;

	private String observacion;

	@Temporal(TemporalType.DATE)
	private Date fechadecreacion;

	@Temporal(TemporalType.DATE)
	private Date fecharecibida;
	
	 
	 private double Total;	
/*
	// Acá indicamos que muchas(clase en la que estamos) Facturas un cliente,muchas
	// Facturas un cliente, un cliente una sola factura
	// Lazy es para evitar que traiga todos los registros de una, los va gestionando
	// a medida que los ncecesitamos
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;*/

	@PrePersist
	public void prePersist() {

		fechadecreacion = new Date();
		fecharecibida = new Date();
	}

	// establecemos relación entre la factura y sus líneas, recordemos que no es
	// necesario a la inversa, es decir linea-facturas
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// hago join un campo clave foránea en ItemFacturas, tabla facturaItems de la
	// relación
	// al no ser un relación en ambos sentidos hay que especificar si o si la clave
	// foránea, es la diferencia con mappedby
	// RELACION UNIDIRECCIONAL
	@JoinColumn(name = "orden_id")

	private List<OrderDetail> orderdetail;

	// acá genero el constructor

	public Order() {
		this.orderdetail = new ArrayList<OrderDetail>();
	}


	public Order(Long id, @NotEmpty String numero, String observacion, Date fechadecreacion, Date fecharecibida,
			double total, List<OrderDetail> orderdetail) {
		super();
		this.id = id;
		this.numero = numero;
		this.observacion = observacion;
		this.fechadecreacion = fechadecreacion;
		this.fecharecibida = fecharecibida;
		Total = total;
		this.orderdetail = orderdetail;
	}




	public void setTotal(double total) {
		Total = total;
	}

	public double getTotal() {
		return Total;
	}

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechadecreacion() {
		return fechadecreacion;
	}

	public void setFechadecreacion(Date fechadecreacion) {
		this.fechadecreacion = fechadecreacion;
	}

	public Date getFecharecibida() {
		return fecharecibida;
	}

	public void setFecharecibida(Date fecharecibida) {
		this.fecharecibida = fecharecibida;
	}



	public List<OrderDetail> getOrderdetail() {
		return orderdetail;
	}

	public void setOrderdetail(List<OrderDetail> orderdetail) {
		this.orderdetail = orderdetail;
	}
	
	

	@Override
	public String toString() {
		return "Order [id=" + id + ", numero=" + numero + ", observacion=" + observacion + ", fechadecreacion="
				+ fechadecreacion + ", fecharecibida=" + fecharecibida + ",  orderdetail="
				+ orderdetail + "]";
	}

}
