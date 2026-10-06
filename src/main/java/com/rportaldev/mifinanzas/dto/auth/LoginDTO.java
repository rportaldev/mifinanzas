package com.rportaldev.mifinanzas.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {

	@NotBlank(message = "El correo no puede estar vacio")
	@Email(message = "El correo no es valido")
	private String correo;
	
	@NotBlank(message = "El password no puede estar vacio")
	private String password;
}
