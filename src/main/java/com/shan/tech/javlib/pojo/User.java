package com.shan.tech.javlib.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@Data
public class User implements Serializable {

    private Long id;

    private String name;

    private String password;

    private Date createdDate;

    private Date updatedDate;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob)
            return true;
        if (!ob.getClass().equals(User.class))
            return false;
        return this.getId().equals(((User) ob).getId());
    }
}
