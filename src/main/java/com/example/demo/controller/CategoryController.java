package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	CategoryService categoryTestService;

	@PostMapping
	public Category saveCategoryData(@RequestBody Category category) {
		Category categorySave = new Category();
		try {
			categorySave = categoryTestService.save(category);
			log.info("Category Request:{}", category);
		} catch (CategoryNotFoundException e) {
			log.error("Exception in save data in CategoryTestController" + e.getMessage());
		}
		return categorySave;

	}

	@GetMapping
	public List<Category> findAllCategory() {
		List<Category> category = new ArrayList<>();
		try {
			category = categoryTestService.findAll();
			log.info("Getting all categories");

		} catch (CategoryNotFoundException e) {
			log.info("Exception in finaAll data in CategoryTestController" + e.getMessage());

		}

		return category;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdCategory(@PathVariable("id") Long id) {
		try {
			if (id != null) {
				Category category = categoryTestService.findById(id);
				log.info("Getting with id categories");
				return ResponseEntity.ok(category);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (CategoryNotFoundException e) {
			log.error("Exception in findById data in CategoryTestController: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category id not found");
		}
	}

	@PutMapping
	public ResponseEntity<?> updateCategory(@RequestBody Category category) {
		try {
			if (category.getId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category ID is required for update");
			}
			log.info("Update Category Request: {}", category);
			Category updatedCategory = categoryTestService.update(category);
			return ResponseEntity.ok(updatedCategory);
		} catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category not found");

		}
	}

	@DeleteMapping("/{id}")
	public String deleteCategory(@PathVariable("id") Long id) {
		try {
			return categoryTestService.delete(id);
		} catch (CategoryNotFoundException e) {
			log.error("Error deleting category with ID {}: {}", id, e.getMessage(), e);
			return "Failed to delete category. Check logs for details.";
		}
	}

}
