package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.DTO.CategoryDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create a category", description = "Add a new Category object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return the created category and the created HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto category) {
        CategoryDto createdCategory = null;
        try {
            createdCategory = categoryService.createCategory(category);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @Operation(summary = "Return all category", description = "Return the list of all the category from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return category list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllCategory() {
        Iterable<CategoryDto> categoryList = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

}
