package com.rportaldev.mifinanzas.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AuthResponseDTO {

	private String token;
	private String tipoToken;
	private String nombre;
	private String correo;
}
