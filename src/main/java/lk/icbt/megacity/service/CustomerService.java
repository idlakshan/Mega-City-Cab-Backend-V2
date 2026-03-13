package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.pagination.PagedResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    UserDTO findByEmail(String email);
    PagedResponseDTO<UserDTO> getAllUsers(int page, int size);
}
