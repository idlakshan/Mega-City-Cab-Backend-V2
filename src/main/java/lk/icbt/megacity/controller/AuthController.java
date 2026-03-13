package lk.icbt.megacity.controller;

import jakarta.validation.Valid;
import lk.icbt.megacity.dto.auth.AuthRequestDTO;
import lk.icbt.megacity.dto.auth.AuthResponseDTO;
import lk.icbt.megacity.dto.auth.RefreshRequestDTO;
import lk.icbt.megacity.dto.auth.SignUpRequestDTO;
import lk.icbt.megacity.service.AuthService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
//        System.out.println(request);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@Valid @RequestBody RefreshRequestDTO request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseUtil> signUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO){
        authService.signUp(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseUtil(201,"Successfully SignUp"));
    }
}
