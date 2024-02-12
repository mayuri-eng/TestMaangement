package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.controller.CategoryControllerTest;
import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.helper.DataHelper;
import com.example.demo.repo.CategoryRepo;

public class CategoryMangementServiceImplTest {

	private static final Logger log = (Logger) LoggerFactory.getLogger(CategoryControllerTest.class);

	@Mock
	private CategoryRepo categoryMnagementRepo;

	@InjectMocks
	private CategoryServiceimpl categoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testSaveCategoryData() {
		try {
			Category category = DataHelper.createTestCategory();
			when(categoryService.save(any(Category.class))).thenReturn(category);
			Category savedCategory = categoryMnagementRepo.save(category);
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
			Category savedCategory = categoryMnagementRepo.save(category);
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
			List<Category> responseEntity = categoryMnagementRepo.findAll();
			assertEquals(category, responseEntity);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void CategoryFindById() {
		try {
			Category category = DataHelper.createTestCategory();
			when(categoryMnagementRepo.findById(1L)).thenReturn(Optional.of(category));
			Category result = categoryService.findById(1L);

			assertEquals(category, result);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testFindByIdCategoryException() {
		try {
			Long categoryId = null;
			Optional<Category> responseEntity = categoryMnagementRepo.findById(categoryId);
			assertNotNull(responseEntity);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

//
	@Test
	public void testUpdateCategory() {
		try {
			Category category = DataHelper.createTestCategory();
			when(categoryMnagementRepo.findById(1L)).thenReturn(Optional.of(category));
			when(categoryMnagementRepo.save(category)).thenReturn(category);

			Category result = categoryService.update(category);

			assertEquals(category, result);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		try {
			Long id = 1L;
			Optional<Category> category = Optional.of(new Category(id, "Category 1", "Description 1", null));

			when(categoryMnagementRepo.findById(id)).thenReturn(category);

			String result = categoryService.delete(id);

			assertEquals("Category with ID " + id + " deleted successfully.", result);
		} catch (CategoryNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

}
