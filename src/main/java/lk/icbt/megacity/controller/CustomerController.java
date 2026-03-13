package lk.icbt.megacity.controller;

import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.pagination.PagedResponseDTO;
import lk.icbt.megacity.service.CustomerService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/current-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUtil> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, "Unauthorized"));
        }

        UserDTO user = customerService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "User not found"));
        }

        return ResponseEntity.ok(new ResponseUtil(200, "Current User", user));
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUtil> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int pageNumber = (page > 0) ? page - 1 : 0;

        PagedResponseDTO<UserDTO> pagedUsers = customerService.getAllUsers(pageNumber, size);
        return ResponseEntity.ok(
                new ResponseUtil(200, "Users retrieved successfully", pagedUsers)
        );
    }
}
