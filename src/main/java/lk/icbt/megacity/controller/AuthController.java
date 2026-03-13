package lk.icbt.megacity.controller;

import jakarta.validation.Valid;
import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.auth.AuthRequestDTO;
import lk.icbt.megacity.dto.auth.AuthResponseDTO;
import lk.icbt.megacity.dto.auth.RefreshRequestDTO;
import lk.icbt.megacity.dto.auth.SignUpRequestDTO;
import lk.icbt.megacity.dto.pagination.PagedResponseDTO;
import lk.icbt.megacity.service.AuthService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
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


    @GetMapping("/current-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUtil> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, "Unauthorized"));
        }

        UserDTO user = authService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "User not found"));
        }
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseUtil(200, "Current User", user));
    }


    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int pageNumber = (page > 0) ? page - 1 : 0;

        PagedResponseDTO<UserDTO> pagedUsers = authService.getAllUsers(pageNumber, size);

        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseUtil(200, "Users retrieved successfully", pagedUsers));
    }
}
