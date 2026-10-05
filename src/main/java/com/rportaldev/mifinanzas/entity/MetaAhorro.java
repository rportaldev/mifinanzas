package com.rportaldev.mifinanzas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rportaldev.mifinanzas.enums.EstadoMeta;

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
@Table(name = "meta_ahorro")
public class MetaAhorro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre",
			nullable = false)
	private String nombre;
	
	@Column(name = "monto_objetivo",
			nullable = false,
			precision = 10,
			scale = 2)
	private BigDecimal montoObjetivo;
	
	@Column(name = "fecha_objetivo",
			nullable = false)
	private LocalDate fechaObjetivo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado",
			nullable = false)
	private EstadoMeta estado;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id",
			nullable = false)
	private Usuario usuario;	
}
