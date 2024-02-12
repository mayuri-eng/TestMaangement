package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Category;

public interface CategoryService {

	public Category save(Category category);

	public Category findById(Long id);

	public Category update(Category category);

	public String delete(Long id);

	public List<Category> findAll();

}
