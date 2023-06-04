package com.agudodiego.quePague.model.response;

import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicListPersonResponse {

    private String username;
    private String email;

    public static BasicListPersonResponse toResponse(Person person) {
        return BasicListPersonResponse.builder()
                .username(person.getUsername())
                .email(person.getEmail())
                .build();
    }
}
