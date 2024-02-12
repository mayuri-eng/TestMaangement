package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Question;

public interface QuestionService {

	public List<Question> findall();

	public Question findById(Long id);

	public Question update(Question question);

	public String delete(Long id);

	public Question save(String categoryName, Question question);

	public void importQuestionsFromExcel(List<MultipartFile> files) throws IOException;


}