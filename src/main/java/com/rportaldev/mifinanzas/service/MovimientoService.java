package com.rportaldev.mifinanzas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.dto.movimiento.MovimientoRequestDTO;
import com.rportaldev.mifinanzas.dto.movimiento.MovimientoResponseDTO;
import com.rportaldev.mifinanzas.entity.Categoria;
import com.rportaldev.mifinanzas.entity.Movimiento;
import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.enums.TipoMovimiento;
import com.rportaldev.mifinanzas.exception.CategoriaInvalidaException;
import com.rportaldev.mifinanzas.exception.MontoInvalidoException;
import com.rportaldev.mifinanzas.exception.RecursoNoEncontradoException;
import com.rportaldev.mifinanzas.repository.CategoriaRepository;
import com.rportaldev.mifinanzas.repository.MovimientoRepository;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoService {

	private final MovimientoRepository movimientoRepository;
	private final CategoriaRepository categoriaRepository;
	private final UsuarioRepository usuarioRepository;
	
	public MovimientoResponseDTO crearMovimiento(String correoUsuario,  MovimientoRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.getCategoriaId(), usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con ID:" + dto.getCategoriaId()));
		
		validarMovimiento(dto, categoria);
		
		Movimiento movimiento = new Movimiento();
		
		movimiento.setTipo(dto.getTipo());
		movimiento.setMonto(dto.getMonto());
		movimiento.setFecha(dto.getFecha());
		movimiento.setDescripcion(dto.getDescripcion());
		movimiento.setCategoria(categoria);
		movimiento.setUsuario(usuario);
			
		movimientoRepository.save(movimiento);
		
		return convertirADTO(movimiento);
		
	}
	
	
	public MovimientoResponseDTO actualizarMovimiento(String correoUsuario, Long id, MovimientoRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		Movimiento movimiento = movimientoRepository.findByIdAndUsuarioId(id, usuario.getId())
				.orElseThrow( () -> new RecursoNoEncontradoException("Movimiento no encontrado con ID: " + id));
		
		Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.getCategoriaId(), usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con ID:" + dto.getCategoriaId()));
		
		validarMovimiento(dto, categoria);
		
		movimiento.setTipo(dto.getTipo());
		movimiento.setMonto(dto.getMonto());
		movimiento.setFecha(dto.getFecha());
		movimiento.setDescripcion(dto.getDescripcion());
		movimiento.setCategoria(categoria);
		
		movimientoRepository.save(movimiento);
		
		return convertirADTO(movimiento);
		
	}
	
	
	public void eliminarMovimiento(String correoUsuario, Long id) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		Movimiento movimiento = movimientoRepository.findByIdAndUsuarioId(id, usuario.getId())
				.orElseThrow( () -> new RecursoNoEncontradoException("Movimiento no encontrado con ID: " + id));
		
		movimientoRepository.delete(movimiento);
	}
	
	
	public Page<MovimientoResponseDTO> listarMovimientos(String correoUsuario, TipoMovimiento tipo,
										Pageable pageable){
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		Page<Movimiento> movimientos;
		
		if (tipo == null) {
			movimientos = movimientoRepository.findByUsuarioId(usuario.getId(), pageable);
		} else {
			movimientos = movimientoRepository
					.findByUsuarioIdAndTipo(usuario.getId(), tipo, pageable);
		}

		return movimientos.map(this::convertirADTO);
	}
	
	
	public Page<MovimientoResponseDTO> listarMovimientos(String correoUsuario,
			TipoMovimiento tipo, Long categoriaId,
			LocalDate desde, LocalDate hasta, Pageable pageable) {

		Usuario usuario = obtenerUsuario(correoUsuario);

		if (desde != null && hasta != null && desde.isAfter(hasta)) {
			throw new MontoInvalidoException(
					"La fecha 'desde' no puede ser posterior a 'hasta'");
		}

		Specification<Movimiento> filtros = (root, query, cb) -> {

			List<Predicate> condiciones = new ArrayList<>();

			condiciones.add(cb.equal(root.get("usuario").get("id"), usuario.getId()));

			if (tipo != null) {
				condiciones.add(cb.equal(root.get("tipo"), tipo));
			}
			if (categoriaId != null) {
				condiciones.add(cb.equal(root.get("categoria").get("id"), categoriaId));
			}
			if (desde != null) {
				condiciones.add(cb.greaterThanOrEqualTo(root.get("fecha"), desde));
			}
			if (hasta != null) {
				condiciones.add(cb.lessThanOrEqualTo(root.get("fecha"), hasta));
			}

			return cb.and(condiciones.toArray(new Predicate[0]));
		};

		return movimientoRepository.findAll(filtros, pageable).map(this::convertirADTO);
	}
	
	private void validarMovimiento(MovimientoRequestDTO dto, Categoria categoria) {
		
		if(dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
			throw new MontoInvalidoException("El monto debe ser mayor a cero");
		}
		
		if(dto.getTipo() != categoria.getTipo()) {
			throw new CategoriaInvalidaException(
					"El movimiento es de tipo " + dto.getTipo()
					+ " pero la categoria '" + categoria.getNombre()
					+ "' es de tipo " + categoria.getTipo());
		}
	}
	
	
	
	
	private Usuario obtenerUsuario(String correoUsuario) {

		return usuarioRepository.findByCorreo(correoUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Usuario no encontrado con correo: " + correoUsuario));
	}
	
	private MovimientoResponseDTO convertirADTO(Movimiento movimiento) {
		
		return new MovimientoResponseDTO(
				movimiento.getId(),
				movimiento.getTipo(),
				movimiento.getMonto(),
				movimiento.getFecha(),
				movimiento.getDescripcion(),
				movimiento.getCategoria().getNombre()
				);
	}
}
