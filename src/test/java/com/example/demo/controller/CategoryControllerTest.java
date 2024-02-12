package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.helper.DataHelper;
import com.example.demo.service.CategoryService;

public class CategoryControllerTest {

	private static final Logger log = (Logger) LoggerFactory.getLogger(CategoryControllerTest.class);

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController categoryController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveCategoryData() {
		try {
			Category category = DataHelper.createTestCategory();
			when(categoryService.save(any(Category.class))).thenReturn(category);
			Category savedCategory = categoryController.saveCategoryData(category);
			assertNotNull(savedCategory);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testSaveCategoryDataException() {
		try {
			Category category = new Category();
			when(categoryService.save(any(Category.class)))
					.thenThrow(new CategoryNotFoundException("Category not found"));
			Category savedCategory = categoryController.saveCategoryData(category);
			assertNotNull(savedCategory);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}
	
	@Test
	public void CategoryFindAll() {
		try {
			List<Category> category = new ArrayList<>();
			when(categoryService.findAll()).thenReturn(category);
			List<Category> responseEntity = categoryController.findAllCategory();			
			assertEquals(category, responseEntity);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}


	@Test
	public void CategoryFindById() {
		try {
			Long categoryId = 1L;
			Category category = DataHelper.createTestCategory();
			when(categoryService.findById(categoryId)).thenReturn(category);
			ResponseEntity<?> responseEntity = categoryController.findByIdCategory(1L);
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			Category actualCategory = (Category) responseEntity.getBody();
			assertEquals(category, actualCategory);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testFindByIdCategoryException() {
		try {
			Long categoryId = null;
			when(categoryService.findById(categoryId))
					.thenThrow(new CategoryNotFoundException("Category id not found"));
			ResponseEntity<?> responseEntity = categoryController.findByIdCategory(null);
			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
			Category actualCategory = (Category) responseEntity.getBody();
			assertNull(actualCategory);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testUpdateCategory() {
		try {
			Category category = DataHelper.createTestCategory();
			when(categoryService.update(category)).thenReturn(category);
			ResponseEntity<?> updatedCategory = categoryController.updateCategory(category);
			assertEquals(HttpStatus.OK, updatedCategory.getStatusCode());
			Category actualCategory = (Category) updatedCategory.getBody();
			assertEquals(category, actualCategory);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testUpdateCategoryException() {
		try {
			Category category = new Category();
			when(categoryService.update(category))
					.thenThrow(new CategoryNotFoundException("Category ID is required for update"));
			ResponseEntity<?> updatedCategory = categoryController.updateCategory(category);
			assertEquals(HttpStatus.BAD_REQUEST, updatedCategory.getStatusCode());
			assertNotNull(updatedCategory);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteCategory() {
		try {
			Long categoryId = 1L;
			String expectedResponse = "Deleted category with ID: " + categoryId;
			when(categoryService.delete(categoryId)).thenReturn(expectedResponse);
			String response = categoryController.deleteCategory(categoryId);
			assertEquals(expectedResponse, response);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}
	
	@Test
	public void testDeleteCategoryException() {
		try {
			Long categoryId = null;
			String expectedResponse = "Failed to delete category. Check logs for details.";
			when(categoryService.delete(categoryId)).thenThrow(new CategoryNotFoundException("Category ID is required for update"));
			String response = categoryController.deleteCategory(categoryId);
			assertEquals(expectedResponse, response);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}
}

