package com.agudodiego.quePague.service.interfaces;

import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.request.LoginPersonRequest;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.AuthenticationResponse;
import com.agudodiego.quePague.model.response.BasicListPersonResponse;
import com.agudodiego.quePague.model.response.PersonResponse;

import java.util.List;

public interface PersonService {

    String register(RegisterPersonRequest request);
    AuthenticationResponse login(LoginPersonRequest request) throws ErrorProcessException;
    PersonResponse getOneByUsername(String username) throws ErrorProcessException;
    List<BasicListPersonResponse> getBasicList() throws ErrorProcessException;
    String deletePerson(String username) throws ErrorProcessException;

}

