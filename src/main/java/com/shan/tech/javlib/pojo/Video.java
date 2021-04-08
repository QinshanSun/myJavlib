package com.shan.tech.javlib.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class Video {

  private Long id;

  private String label;

  private String number;

  private String title;

  private int year;

  private String rated;

  private String released;

  private String runTime;

  private String director;

  private String plot;

  private String poster;

  private double rating;

  private double metaScore;

  private int votes;

  private String producer;

  private String publisher;

  private Date createdDate;

  private Date updatedDate;

  private List<Actor> actorList;

  private List<Genre> genreList;
}
