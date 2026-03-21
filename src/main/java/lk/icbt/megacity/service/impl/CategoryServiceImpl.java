package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.CategoryDTO;
import lk.icbt.megacity.dto.UserDTO;
import lk.icbt.megacity.entity.Category;
import lk.icbt.megacity.repo.CategoryRepo;
import lk.icbt.megacity.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return  modelMapper.map(categories,new TypeToken<List<CategoryDTO>>() {}.getType());
    }
}
