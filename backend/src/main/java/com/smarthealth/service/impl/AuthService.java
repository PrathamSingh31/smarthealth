package com.smarthealth.service.impl;

import com.smarthealth.model.dto.RegisterRequest;
import com.smarthealth.model.dto.AuthRequest;
import com.smarthealth.model.dto.AuthResponse;

public interface AuthService {
    String registerUser(RegisterRequest request);
    AuthResponse loginUser(AuthRequest request);
}
