package com.rportaldev.mifinanzas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "aporte_ahorro")
public class AporteAhorro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "monto",
			nullable = false,
			precision = 10,
			scale = 2)
	private BigDecimal monto;
	
	@Column(name = "fecha",
			nullable = false)
	private LocalDate fecha;
	
	@ManyToOne
	@JoinColumn(name = "meta_ahorro_id",
			nullable = false)
	private MetaAhorro metaAhorro;
}
