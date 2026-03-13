package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.UserDTO;

public interface CustomerService {
    UserDTO findByEmail(String email);
}
