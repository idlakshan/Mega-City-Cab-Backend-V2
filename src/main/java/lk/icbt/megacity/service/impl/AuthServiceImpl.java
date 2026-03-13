package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.auth.AuthRequestDTO;
import lk.icbt.megacity.dto.auth.AuthResponseDTO;
import lk.icbt.megacity.dto.auth.SignUpRequestDTO;
import lk.icbt.megacity.dto.pagination.PagedResponseDTO;
import lk.icbt.megacity.entity.Role;
import lk.icbt.megacity.entity.User;
import lk.icbt.megacity.repo.RoleRepo;
import lk.icbt.megacity.repo.UserRepo;
import lk.icbt.megacity.service.AuthService;
import lk.icbt.megacity.service.CustomUserDetailsService;
import lk.icbt.megacity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return new AuthResponseDTO(accessToken, refreshToken, userDTO);
    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {
        try {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (!jwtUtil.isTokenValid(refreshToken, userDetails)) {
                throw new IllegalArgumentException("Invalid or expired refresh token");
            }

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);
            User user = userRepo.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            return new AuthResponseDTO(newAccessToken, refreshToken, userDTO);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid refresh token", e);
        }
    }

    @Override
    public void signUp(SignUpRequestDTO signUpRequestDTO) {

        if(userRepo.findByEmail(signUpRequestDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }

        if(userRepo.findByPhone(signUpRequestDTO.getPhone()).isPresent()){
            throw new RuntimeException("Phone already registered");
        }

        if(userRepo.findByNic(signUpRequestDTO.getNic()).isPresent()){
            throw new RuntimeException("NIC already registered");
        }

        User user = modelMapper.map(signUpRequestDTO, User.class);

        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));

        Role customerRole = roleRepo.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));

        user.getRoles().add(customerRole);

        userRepo.save(user);
    }



    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return modelMapper.map(user,UserDTO.class);

    }

    @Override
    public PagedResponseDTO<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepo.findAll(pageable);

        List<UserDTO> userDTOs = modelMapper.map(
                usersPage.getContent(),
                new TypeToken<List<UserDTO>>() {}.getType()
        );

        return new PagedResponseDTO<>(new PageImpl<>(userDTOs, pageable, usersPage.getTotalElements()));
    }


}
