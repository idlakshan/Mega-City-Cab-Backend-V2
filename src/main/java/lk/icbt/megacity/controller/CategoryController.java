package lk.icbt.megacity.controller;

import lk.icbt.megacity.dto.CategoryDTO;
import lk.icbt.megacity.service.CategoryService;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<ResponseUtil> getCategories() {
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseUtil(200, "Categories retrieved successfully",allCategories));
    }
}
