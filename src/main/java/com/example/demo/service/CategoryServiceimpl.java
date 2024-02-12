package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.repo.CategoryRepo;

@Service
public class CategoryServiceimpl implements CategoryService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(CategoryServiceimpl.class);

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public Category save(Category category) {
		try {
			return categoryRepo.save(category);
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Error saving category: " + e.getMessage());
		}
	}

	@Override
	public List<Category> findAll() {
		try {
			return categoryRepo.findAll();
		} catch (CategoryNotFoundException e) {
			throw new ServiceException("Error retrieving categories: " + e.getMessage(), e);
		}
	}

	@Override
	public Category findById(Long id) {
		try {
			Optional<Category> category = categoryRepo.findById(id);
			if (category.isPresent()) {
				return categoryRepo.findById(id).get();
			} else {
				throw new CategoryNotFoundException("Category not found for ID: " + id);

			}

		} catch (CategoryNotFoundException e) {
			throw new ServiceException("Error retrieving category: " + e.getMessage(), e);

		}
	}

	@Override
	public Category update(Category category) {
		long categoryId = category.getId();
		try {
			Category existingCategory = findById(categoryId);
			if (existingCategory == null) {
				throw new CategoryNotFoundException("Category not found for ID: " + categoryId);
			}

			return categoryRepo.save(existingCategory);
		} catch (CategoryNotFoundException e) {
			throw new ServiceException("Error updating category: " + e.getMessage(), e);
		}

	}

	@Override
	public String delete(Long id) {
		try {
			Optional<Category> category = categoryRepo.findById(id);
			if (category.isEmpty()) {
				return "Category id not present";
			} else {
				categoryRepo.deleteById(id);
				return "Category with ID " + id + " deleted successfully.";
			}
		} catch (CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Category not found for ID: " + id);
		}
	}

}
