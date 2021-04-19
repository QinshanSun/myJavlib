package com.shan.tech.javlib.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Actor {
  @JsonIgnore
  private Long id;

  private String name;

  private String label;

  private String type;

  private Date createdDate;

  private Date updatedDate;

  private String usedName;
}
