package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.auth.AuthRequest;
import lk.icbt.megacity.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse refreshToken(String refreshToken);
}
