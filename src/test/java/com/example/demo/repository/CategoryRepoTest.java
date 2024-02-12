//package com.example.demo.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.demo.entity.Category;
//import com.example.demo.repo.CategoryRepo;
//
//
//public class CategoryRepoTest {
//	private static final Logger log = (Logger) LoggerFactory.getLogger(CategoryRepoTest.class);
//
//	@InjectMocks
//	private CategoryRepo categoryRepository;
//
//	@BeforeEach
//	public void setUp() {
//		Category category1 = new Category();
//		category1.setName("Category1");
//		categoryRepository.save(category1);
//
//		Category category2 = new Category();
//		category2.setName("Category2");
//		categoryRepository.save(category2);
//	}
//
//	@AfterEach
//	public void tearDown() {
//		categoryRepository.deleteAll();
//	}
//
//	@Test
//	public void testFindByName_Exists() {
//		Optional<Category> foundCategory = categoryRepository.findByName("Category1");
//		assertTrue(foundCategory.isPresent());
//		assertEquals("Category1", foundCategory.get().getName());
//	}
//
//	@Test
//	public void testFindByName_NotExists() {
//		Optional<Category> foundCategory = categoryRepository.findByName("NonExistentCategory");
//		assertFalse(foundCategory.isPresent());
//	}
//}
