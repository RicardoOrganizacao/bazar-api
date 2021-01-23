package com.ricbap.bazar.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ricbap.bazar.domain.enums.EstadoPagamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // <--- Mapeamento de Heranca gerando uma tabela para cada subClasse
@Table(name = "pagamento")
public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY) desnecessaria pois o id do Pagamento será o mesmo Id do Pedido
	private Integer id;
	private Integer estado;  //<-- Enum e mudanca no constructor e get e set
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId // <--- Terá o mesmo numero do Pedido
	private Pedido pedido;
	
	
	public Pagamento() {}


	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		this.id = id;
		this.estado = (estado == null) ? null : estado.getCod();
		this.pedido = pedido;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}
	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
