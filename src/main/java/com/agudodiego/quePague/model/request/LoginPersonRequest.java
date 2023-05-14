package com.agudodiego.quePague.model.request;

import com.agudodiego.quePague.model.entity.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginPersonRequest {

    @NotBlank(message = "username can't be blank")
    @Size(min = 6, message = "username must have more than 5 characters")
    private String username;
    @NotBlank(message = "Password can't be blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$", message = "Password must have more than 7 characters and have at least one uppercase, one lowercase and one number")
    private String pass;

}
