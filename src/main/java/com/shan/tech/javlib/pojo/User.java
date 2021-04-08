package com.shan.tech.javlib.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
public class User implements Serializable {

    private Long id;

    private String name;

    private String password;

    private Date created_date;

    private Date updated_date;



}
