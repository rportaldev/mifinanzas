package com.rportaldev.mifinanzas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.dto.categoria.CategoriaRequestDTO;
import com.rportaldev.mifinanzas.dto.categoria.CategoriaResponseDTO;
import com.rportaldev.mifinanzas.entity.Categoria;
import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.exception.CategoriaInvalidaException;
import com.rportaldev.mifinanzas.exception.RecursoNoEncontradoException;
import com.rportaldev.mifinanzas.repository.CategoriaRepository;
import com.rportaldev.mifinanzas.repository.MovimientoRepository;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;
	private final UsuarioRepository usuarioRepository;
	private final MovimientoRepository movimientoRepository;
	
	public CategoriaResponseDTO crearCategoria(String correoUsuario, CategoriaRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);

		if (categoriaRepository.existsByNombreAndUsuarioIdAndTipo(
				dto.getNombre(), 
				usuario.getId(), 
				dto.getTipo())) {
			throw new CategoriaInvalidaException(
					"Ya tienes una categoria llamada '" + dto.getNombre()
					+ "' de tipo " + dto.getTipo());
		}
		
		Categoria categoria = new Categoria();
		
		categoria.setNombre(dto.getNombre());
		categoria.setTipo(dto.getTipo());
		categoria.setUsuario(usuario);
		
		categoriaRepository.save(categoria);
		
		return convertirADTO(categoria);
	}
	
	
	public CategoriaResponseDTO actualizarCategoria(String correoUsuario, Long id, CategoriaRequestDTO dto) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException(
							"Categoria no encontrado con ID:" + id));
		
		boolean cambioNombreOTipo = !categoria.getNombre().equals(dto.getNombre())
				|| categoria.getTipo() != dto.getTipo();

		if (cambioNombreOTipo && categoriaRepository.existsByNombreAndUsuarioIdAndTipo(
				dto.getNombre(), usuario.getId(), dto.getTipo())) {
			throw new CategoriaInvalidaException(
					"Ya tienes una categoria llamada '" + dto.getNombre()
					+ "' de tipo " + dto.getTipo());
		}
		
		categoria.setNombre(dto.getNombre());
		categoria.setTipo(dto.getTipo());
		
		categoriaRepository.save(categoria);
		
		return convertirADTO(categoria);
	}
	
	
	public void eliminarCategoria(String correoUsuario, Long id) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuario.getId())
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Categoria no encontrada con ID: " + id));
		
		if (movimientoRepository.existsByCategoriaId(categoria.getId())) {
			throw new CategoriaInvalidaException(
					"No se puede eliminar la categoria '" + categoria.getNombre()
					+ "' porque tiene movimientos asociados. "
					+ "Elimina o reasigna esos movimientos primero.");
		}
		
		categoriaRepository.delete(categoria);
	}
	
	
	public 	List<CategoriaResponseDTO> listarCategorias(String correoUsuario){
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		return categoriaRepository.findByUsuarioId(usuario.getId())
				.stream()
				.map(this::convertirADTO)
				.toList();
	}
	
	private Usuario obtenerUsuario(String correoUsuario) {

		return usuarioRepository.findByCorreo(correoUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Usuario no encontrado con correo: " + correoUsuario));
	}
	
	private CategoriaResponseDTO convertirADTO(Categoria categoria) {
		
		return new CategoriaResponseDTO(
				categoria.getId(),
				categoria.getNombre(),
				categoria.getTipo()
				);
	}
}
