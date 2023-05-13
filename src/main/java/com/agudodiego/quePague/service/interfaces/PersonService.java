package com.agudodiego.quePague.service.interfaces;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.request.LoginPersonRequest;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.PersonResponse;

public interface PersonService {

    String saveOne(RegisterPersonRequest request);
    PersonResponse getOne(LoginPersonRequest request) throws ErrorProcessException;
    PersonResponse getOneByUsername(String username) throws ErrorProcessException;

}

