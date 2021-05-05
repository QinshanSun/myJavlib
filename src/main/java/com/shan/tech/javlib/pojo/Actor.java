package com.shan.tech.javlib.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Actor implements Serializable {
  @JsonIgnore
  private Long id;

  private String name;

  private String label;

  private String type;

  private Date createdDate;

  private Date updatedDate;

  private String usedName;

  @JsonCreator
  public Actor(@JsonProperty("name") String name, @JsonProperty("label") String label, @JsonProperty("type") String type) {
    this.name = name;
    this.label = label;
    this.type = type;
  }
}
