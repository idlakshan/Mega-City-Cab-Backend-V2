package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.auth.AuthRequest;
import lk.icbt.megacity.dto.auth.AuthResponse;
import lk.icbt.megacity.service.AuthService;
import lk.icbt.megacity.service.CustomUserDetailsService;
import lk.icbt.megacity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        try {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (!jwtUtil.isTokenValid(refreshToken, userDetails)) {
                throw new IllegalArgumentException("Invalid or expired refresh token");
            }

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);

            // String newRefreshToken = jwtService.generateRefreshToken(userDetails);
            return new AuthResponse(newAccessToken, refreshToken);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid refresh token", e);
        }
    }
}
