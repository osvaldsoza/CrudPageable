package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {
  @Autowired
  TutorialRepository tutorialRepository;

  @GetMapping("/tutorials")
  public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
      @RequestParam(required = false) String title,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size) {

    try {
      List<Tutorial> tutorials = new ArrayList<Tutorial>();
      Pageable paging = PageRequest.of(page, size);
      
      Page<Tutorial> pageResponse;
      if (title == null)
        pageResponse = tutorialRepository.findAll(paging);
      else
        pageResponse = tutorialRepository.findByTitleContainingIgnoreCase(title, paging);

      tutorials = pageResponse.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("tutorials", tutorials);
      response.put("currentPage", pageResponse.getNumber());
      response.put("totalItems", pageResponse.getTotalElements());
      response.put("totalPages", pageResponse.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/tutorials/published")
  public ResponseEntity<Map<String, Object>> findByPublished(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size) {

    try {      
      List<Tutorial> tutorials = new ArrayList<Tutorial>();
      Pageable paging = PageRequest.of(page, size);
      
      Page<Tutorial> pageTuts = tutorialRepository.findByPublished(true, paging);
      tutorials = pageTuts.getContent();
      
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      
      Map<String, Object> response = new HashMap<>();
      response.put("tutorials", tutorials);
      response.put("currentPage", pageTuts.getNumber());
      response.put("totalItems", pageTuts.getTotalElements());
      response.put("totalPages", pageTuts.getTotalPages());
      
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  @Autowired
//  TutorialRepository tutorialRepository;
//
//  @GetMapping("/tutorials")
//  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
//    try {
//      List<Tutorial> tutorials = new ArrayList<Tutorial>();
//
//      if (title == null)
//        tutorialRepository.findAll().forEach(tutorials::add);
//      else
//        tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
//
//      if (tutorials.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//
//      return new ResponseEntity<>(tutorials, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
//
//  @GetMapping("/tutorials/{id}")
//  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
//    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
//
//    if (tutorialData.isPresent()) {
//      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//  }
//
//  @PostMapping("/tutorials")
//  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
//    try {
//      Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
//      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
//    } catch (Exception e) {
//      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
//
//  @PutMapping("/tutorials/{id}")
//  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
//    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
//
//    if (tutorialData.isPresent()) {
//      Tutorial _tutorial = tutorialData.get();
//      _tutorial.setTitle(tutorial.getTitle());
//      _tutorial.setDescription(tutorial.getDescription());
//      _tutorial.setPublished(tutorial.isPublished());
//      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//  }
//
//  @DeleteMapping("/tutorials/{id}")
//  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
//    try {
//      tutorialRepository.deleteById(id);
//      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    } catch (Exception e) {
//      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
//
//  @DeleteMapping("/tutorials")
//  public ResponseEntity<HttpStatus> deleteAllTutorials() {
//    try {
//      tutorialRepository.deleteAll();
//      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    } catch (Exception e) {
//      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
//
//  @GetMapping("/tutorials/published")
//  public ResponseEntity<List<Tutorial>> findByPublished() {
//    try {
//      List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
//
//      if (tutorials.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//      return new ResponseEntity<>(tutorials, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

}
