package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {
//  List<Tutorial> findByTitleContaining(String title);
//  List<Tutorial> findByPublished(boolean published);
  Page<Tutorial> findByPublished(boolean published, Pageable pageable);

  Page<Tutorial> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}