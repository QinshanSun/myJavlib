package com.shan.tech.javlib.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class Video implements Serializable {

  @JsonIgnore
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

  @JsonCreator
  public Video(@JsonProperty("title") String title,
               @JsonProperty("label") String label,
               @JsonProperty("number") String number,
               @JsonProperty("released") String released) {
    this.title = title;
    this.label = label;
    this.number = number;
    this.released = released;
  }
}
