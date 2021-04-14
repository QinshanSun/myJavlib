package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genre")
public class GenreController {

  private GenreService genreService;

  @Autowired
  public void setGenreService(GenreService genreService) {
    this.genreService = genreService;
  }
}
