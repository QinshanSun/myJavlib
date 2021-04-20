package com.shan.tech.javlib.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Genre {

  @JsonIgnore
  private Long id;

  private String name;

  private String label;

  @JsonCreator
  public Genre(@JsonProperty("name") String name, @JsonProperty("label") String label){
    this.label = label;
    this.name = name;
  }
}
