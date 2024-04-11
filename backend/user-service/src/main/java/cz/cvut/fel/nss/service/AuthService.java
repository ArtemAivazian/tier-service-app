package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.model.AuthenticationRequest;
import cz.cvut.fel.nss.model.AuthenticationResponse;
import cz.cvut.fel.nss.model.RegisterRequest;
import cz.cvut.fel.nss.model.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
