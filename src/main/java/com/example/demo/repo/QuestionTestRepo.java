package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.QuestionTest;

@Repository
public interface QuestionTestRepo extends JpaRepository<QuestionTest, Long>  {


}
