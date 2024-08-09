package com.matejamusa.personal_finance.controller;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategoryForUser(Authentication authentication, @RequestBody @Valid Category category) {
        User user = (User) authentication.getPrincipal();

        Category createdCategory = categoryService.createCategoryForUser(category, user.getId());
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllByUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Category> categories = categoryService.getAllByUserId(user.getId());
        return ResponseEntity.ok(categories);
    }
}
