package dev.nelon.dreamshops.request;

import lombok.Data;

@Data
public class UserCreateRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
