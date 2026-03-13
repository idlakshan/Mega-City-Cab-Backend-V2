package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.dto.pagination.PagedResponseDTO;
import lk.icbt.megacity.entity.User;
import lk.icbt.megacity.repo.UserRepo;
import lk.icbt.megacity.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
