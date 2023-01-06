package com.tyr.todo.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    @NotEmpty
    @Size(min = 4, message = "Username should have at least 4 characters")
    @Size(max = 50, message = "Username should have maximum 50 characters")
    private String username;
    @NotEmpty
    @Size(min = 2, message = "Password should have at least 6 characters")
    @Size(max = 50, message = "Password should have maximum 100 characters")
    private String password;
    @NotEmpty
    @Size(min = 2, message = "First name should have at least 2 characters")
    @Size(max = 50, message = "First name should have maximum 50 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = "Last name should have at least 2 characters")
    @Size(max = 50, message = "Last name should have maximum 50 characters")
    private String lastName;
    private Role role;
}
