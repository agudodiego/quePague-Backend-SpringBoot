package com.agudodiego.quePague.model.response;

import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonResponse {

    private String username;
    private Set<Payment> payments;

    public static PersonResponse toResponse(Person person) {
        return PersonResponse.builder()
                .username(person.getUsername())
                .payments(person.getPayments())
                .build();
    }
}
