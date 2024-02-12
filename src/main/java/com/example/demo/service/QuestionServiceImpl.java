package com.example.demo.service;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import com.example.demo.entity.Question;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.QuestionRepo;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Override
	public Question save(String name,Question question) {
		try {
			
			
			Category category = categoryRepo.findByName(name)
					.orElseThrow(() -> new IllegalArgumentException("Category not found with name: " + name));
			question.setCategory(category);
			return questionRepo.save(question);
	       
	    } catch (QuestionNotFoundException e) { 
	        throw new QuestionNotFoundException("Error saving question: " + e.getMessage());
	    }
	}

	@Override
	public List<Question> findall() {
		try {
		return questionRepo.findAll();
		}catch (Exception e) {
			throw new ServiceException("Error retrieving categories: " + e.getMessage(), e);
		}
	}

	@Override
	public Question findById(Long id) {
		try {
			Optional<Question> question = questionRepo.findById(id);
			if (question.isPresent()) {
				return question.get();
			} else {
				throw new QuestionNotFoundException("question not found for ID: " + id);
			}
		} catch (QuestionNotFoundException e) {
			throw new ServiceException("Error retrieving Question: " + e.getMessage(), e);

		}
	}

	@Override
	public Question update(Question question) {
		long questionId = question.getQuestionid();
		try {
			Question questionIdFind = findById(questionId);
			if (questionIdFind == null) {
				throw new CategoryNotFoundException("Question not found for ID: " + questionId);
			}

			return questionRepo.save(questionIdFind);
		} catch (QuestionNotFoundException e) {
			throw new ServiceException("Error updating Question: " + e.getMessage(), e);
		}

	}

	@Override
	public String delete(Long id) {
		
		Optional<Question> qusetion=questionRepo.findById(id);
		if(qusetion.isEmpty()) {		
		return "id is not present";
		}else {
			questionRepo.deleteById(id);
			return "question id" +id+ " deleted successfully";
		}
		
	}

	@Override
	public void importQuestionsFromExcel(List<MultipartFile> multipartfiles) throws IOException {
		
		if (!multipartfiles.isEmpty()) {
			List<Question> transactions = new ArrayList<>();
			multipartfiles.forEach(multipartfile -> {
				try {
					XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());
 
					XSSFSheet sheet = workBook.getSheetAt(0);
					for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
						XSSFRow row = sheet.getRow(rowIndex);
						if (rowIndex == 0) {
							continue;
						}	
						
						String question = String.valueOf(row.getCell(0));
						String op1 = String.valueOf(row.getCell(1));
						String op2 = String.valueOf(row.getCell(2));
						String correctAnswer= String.valueOf(row.getCell(3));
						String mark = String.valueOf(row.getCell(6));
						String name = String.valueOf(row.getCell(7));

						Category category = categoryRepo.findByName(name)
								.orElseGet(() -> categoryRepo.save(new Category()));

						Question questionRequest = new Question();
						questionRequest.setQuestion(question);
						questionRequest.setOp1(op1);
						questionRequest.setOp2(op2);
						questionRequest.setCorrectOption(correctAnswer);
						questionRequest.setMark(mark);
						questionRequest.setCategory(category);
						transactions.add(questionRequest);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			if (!transactions.isEmpty()) {
				questionRepo.saveAll(transactions);
			}
		}
	
	}
	
	public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
		int numOfNonEmptyCells = 0;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (row != null) {
				XSSFCell cell = row.getCell(columnIndex);
				if (cell != null && cell.getCellType() != CellType.BLANK) {
					numOfNonEmptyCells++;
				}
			}
		}
		return numOfNonEmptyCells;
	}
}
