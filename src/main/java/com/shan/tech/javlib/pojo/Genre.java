package com.shan.tech.javlib.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Genre {

  @JsonIgnore
  private Long id;

  private String name;

  private String label;
}
