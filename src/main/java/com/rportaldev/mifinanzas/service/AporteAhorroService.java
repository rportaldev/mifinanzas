package com.rportaldev.mifinanzas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.entity.MetaAhorro;
import com.rportaldev.mifinanzas.dto.meta.AporteAhorroRequestDTO;
import com.rportaldev.mifinanzas.dto.meta.AporteAhorroResponseDTO;
import com.rportaldev.mifinanzas.entity.AporteAhorro;
import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.enums.EstadoMeta;
import com.rportaldev.mifinanzas.exception.MetaNoActivaException;
import com.rportaldev.mifinanzas.exception.MontoInvalidoException;
import com.rportaldev.mifinanzas.exception.RecursoNoEncontradoException;
import com.rportaldev.mifinanzas.repository.AporteAhorroRepository;
import com.rportaldev.mifinanzas.repository.MetaAhorroRepository;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AporteAhorroService {

	private final AporteAhorroRepository aporteAhorroRepository;
	private final MetaAhorroRepository metaAhorroRepository;
	private final UsuarioRepository usuarioRepository;
	private final MetaAhorroService metaAhorroService;
	
	
	public AporteAhorroResponseDTO registrarAporte(String correoUsuario, Long metaId, AporteAhorroRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		MetaAhorro meta = metaAhorroRepository.findByIdAndUsuarioId(metaId, usuario.getId())
					.orElseThrow(() -> new RecursoNoEncontradoException(
							"Meta no encontrada con ID: " + metaId));
		
		if (meta.getEstado() != EstadoMeta.ACTIVA) {
			throw new MetaNoActivaException(
					"Solo se pueden aportar a metas activas. Estado actual: " + meta.getEstado());
		}
		
		if(dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
			throw new MontoInvalidoException("El monto debe ser mayor a cero");
		}
		
		AporteAhorro aporteAhorro = new AporteAhorro();
		
		aporteAhorro.setFecha(dto.getFecha());
		aporteAhorro.setMonto(dto.getMonto());
		aporteAhorro.setMetaAhorro(meta);
		
		aporteAhorroRepository.save(aporteAhorro);
		
		metaAhorroService.verificarYActualizarEstado(meta);
		
		return convertirADTO(aporteAhorro);
		
	}
	
	
	public void eliminarAporte(String correoUsuario, Long metaId, Long aporteId) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		MetaAhorro meta = metaAhorroRepository.findByIdAndUsuarioId(metaId, usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Meta no encontrada con ID: " + metaId));
		
		AporteAhorro aporte = aporteAhorroRepository.findByIdAndMetaAhorroId(aporteId, metaId)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Aporte no encontrado con ID: " + aporteId));
		
		aporteAhorroRepository.delete(aporte);
		metaAhorroService.verificarYActualizarEstado(meta);
	}
	
	public List<AporteAhorroResponseDTO> listarAportes(String correoUsuario, Long metaId){
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		metaAhorroRepository.findByIdAndUsuarioId(metaId, usuario.getId())
		.orElseThrow(() -> new RecursoNoEncontradoException(
				"Meta no encontrada con ID: " + metaId));
		
		return aporteAhorroRepository.findByMetaAhorroId(metaId)
				.stream()
				.map(this::convertirADTO)
				.toList();
	}
	
	
	private Usuario obtenerUsuario(String correoUsuario) {
		
		return usuarioRepository.findByCorreo(correoUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Usuario no encontrado con correo: " + correoUsuario));
	}
	
	private AporteAhorroResponseDTO convertirADTO(AporteAhorro aporteAhorro) {
		
		return new AporteAhorroResponseDTO(
				aporteAhorro.getId(),
				aporteAhorro.getMonto(),
				aporteAhorro.getFecha()
				);
	}
}
