package com.springsecutityjwt.demo.service.register;

import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.RegisterResponse;

public interface RegisterService {

    RegisterResponse registerUser(RegisterRequest registerRequest);

}
