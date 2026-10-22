package com.rportaldev.mifinanzas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.dto.meta.MetaAhorroRequestDTO;
import com.rportaldev.mifinanzas.dto.meta.MetaAhorroResponseDTO;
import com.rportaldev.mifinanzas.entity.MetaAhorro;
import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.enums.EstadoMeta;
import com.rportaldev.mifinanzas.exception.MetaNoActivaException;
import com.rportaldev.mifinanzas.exception.RecursoNoEncontradoException;
import com.rportaldev.mifinanzas.repository.AporteAhorroRepository;
import com.rportaldev.mifinanzas.repository.MetaAhorroRepository;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetaAhorroService {

	private final MetaAhorroRepository metaAhorroRepository;
	private final AporteAhorroRepository aporteAhorroRepository;
	private final UsuarioRepository usuarioRepository;
	
	
	public MetaAhorroResponseDTO crearMeta(String correoUsuario, MetaAhorroRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		MetaAhorro meta = new MetaAhorro();
		
		meta.setNombre(dto.getNombre());
		meta.setMontoObjetivo(dto.getMontoObjetivo());
		meta.setFechaObjetivo(dto.getFechaObjetivo());
		meta.setEstado(EstadoMeta.ACTIVA);
		meta.setUsuario(usuario);
		
		metaAhorroRepository.save(meta);
		return convertirADTO(meta);
	}
	
	
	public MetaAhorroResponseDTO actualizarMeta(String correoUsuario, Long id, MetaAhorroRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		MetaAhorro meta = metaAhorroRepository.findByIdAndUsuarioId(id, usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Meta no encontrada con ID: " + id));
		
		if (meta.getEstado() != EstadoMeta.ACTIVA) {
			throw new MetaNoActivaException(
					"Solo se pueden editar metas activas. Estado actual: " + meta.getEstado());
		}
		
		meta.setNombre(dto.getNombre());
		meta.setMontoObjetivo(dto.getMontoObjetivo());
		meta.setFechaObjetivo(dto.getFechaObjetivo());

		verificarYActualizarEstado(meta);
		return convertirADTO(meta);
		
	}


	public void eliminarMeta(String correoUsuario, Long id) {
	
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		MetaAhorro meta = metaAhorroRepository.findByIdAndUsuarioId(id, usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Meta no encontrada con ID: " + id));
		
		if(aporteAhorroRepository.existsByMetaAhorroId(meta.getId())) {
			throw new MetaNoActivaException("No se puede eliminar la Meta '" + meta.getNombre() 
			+ "' porque tiene aportes asociados. "
			+ "Elimina esos aportes primero.");
		}
		
		metaAhorroRepository.delete(meta);
	}
	
	public List<MetaAhorroResponseDTO> listarMetas(String correoUsuario){
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		return metaAhorroRepository.findByUsuarioId(usuario.getId())
				.stream()
				.map(this::convertirADTO)
				.toList();
	}
	
	
	public void verificarYActualizarEstado(MetaAhorro meta) {

		if (meta.getEstado() == EstadoMeta.CANCELADA) {
			return;
		}

		BigDecimal montoAhorrado = aporteAhorroRepository.sumarAportesPorMeta(meta.getId());

		if (montoAhorrado.compareTo(meta.getMontoObjetivo()) >= 0) {
			meta.setEstado(EstadoMeta.COMPLETADA);
		} else {
			meta.setEstado(EstadoMeta.ACTIVA);
		}

		metaAhorroRepository.save(meta);
	}
	
	
	private Usuario obtenerUsuario(String correoUsuario) {
		
		return usuarioRepository.findByCorreo(correoUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Usuario no encontrado con correo: " + correoUsuario));
	}


	private MetaAhorroResponseDTO convertirADTO(MetaAhorro meta) {

		BigDecimal montoAhorrado = aporteAhorroRepository.sumarAportesPorMeta(meta.getId());

		BigDecimal montoFaltante = meta.getMontoObjetivo().subtract(montoAhorrado);

		if (montoFaltante.compareTo(BigDecimal.ZERO) < 0) {
			montoFaltante = BigDecimal.ZERO;
		}

		return new MetaAhorroResponseDTO(
				meta.getId(),
				meta.getNombre(),
				meta.getMontoObjetivo(),
				meta.getFechaObjetivo(),
				meta.getEstado(),
				montoAhorrado,
				montoFaltante);
	}
	
}
