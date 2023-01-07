package com.tyr.todo.auth.model;

import com.tyr.todo.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {

    private String token;
    private String name;
    private Role role;
}
