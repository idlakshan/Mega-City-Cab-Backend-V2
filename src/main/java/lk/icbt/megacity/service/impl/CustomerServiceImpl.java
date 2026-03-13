package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.entity.User;
import lk.icbt.megacity.repo.UserRepo;
import lk.icbt.megacity.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return modelMapper.map(user,UserDTO.class);

    }
}
