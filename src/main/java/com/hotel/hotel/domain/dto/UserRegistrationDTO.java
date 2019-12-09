package com.hotel.hotel.domain.dto;

import com.hotel.hotel.annotation.FieldMatch;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@FieldMatch.List({
		@FieldMatch(first = "password", second = "confirmPassword", message = "The Password Fields Are Not Matched")})


@Data
public class UserRegistrationDTO {
	@NotEmpty
	private String userId;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String password;

	@NotEmpty
	private String confirmPassword;

}
