package com.rportaldev.mifinanzas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.rportaldev.mifinanzas.enums.TipoMovimiento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "movimiento")
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo",
			nullable = false)
	private TipoMovimiento tipo;
	
	@Column(name = "monto",
			nullable = false,
			precision = 10,
			scale = 2)
	private BigDecimal monto;
	
	@Column(name = "fecha",
			nullable = false)
	private LocalDate fecha;
	
	@Column(name = "descripcion",
			nullable = false)
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id",
				nullable = false)
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id",
				nullable = false)
	private Usuario usuario;
}
