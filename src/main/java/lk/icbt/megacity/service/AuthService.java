package lk.icbt.megacity.service;

import jakarta.validation.Valid;
import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.auth.AuthRequestDTO;
import lk.icbt.megacity.dto.auth.AuthResponseDTO;
import lk.icbt.megacity.dto.auth.SignUpRequestDTO;
import lk.icbt.megacity.dto.pagination.PagedResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO request);
    AuthResponseDTO refreshToken(String refreshToken);
    void signUp(@Valid SignUpRequestDTO signUpRequestDTO);
    UserDTO findByEmail(String email);
    PagedResponseDTO<UserDTO> getAllUsers(int page, int size);

}
