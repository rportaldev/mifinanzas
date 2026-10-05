package com.rportaldev.mifinanzas.entity;

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
@Table(name = "categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre",
			nullable = false)
	private String nombre;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo",
			nullable = false)
	private TipoMovimiento tipo;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id",
				nullable = false)
	private Usuario usuario;
		
}
