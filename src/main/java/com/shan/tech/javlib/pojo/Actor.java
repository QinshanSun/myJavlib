package com.shan.tech.javlib.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Actor {
  private Long id;

  private String name;

  private String label;

  private String type;

  private Date createdDate;

  private Date updatedDate;

  private List<String> usedName;
}
