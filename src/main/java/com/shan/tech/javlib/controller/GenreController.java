package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.pojo.Genre;
import com.shan.tech.javlib.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

  private GenreService genreService;

  @Autowired
  public void setGenreService(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping(value = "/{label}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Genre> getGenreByLabel(@PathVariable String label) {
    Genre genreOptional = genreService.findByLabel(label);
    return new ResponseEntity<>(genreOptional, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Genre> getGenreByName(@RequestParam(name = "name") String name) {
    List<Genre> genreList = genreService.findGenresByName(name);
    return new ResponseEntity(genreList, HttpStatus.OK);
  }

}
