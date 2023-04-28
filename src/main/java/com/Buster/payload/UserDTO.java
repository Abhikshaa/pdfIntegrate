package com.Buster.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

     private long id;
       @NotEmpty
       @Size(min = 3,max = 9,message = "valid name")
     private String name;
       @NotEmpty
       @Size(min = 4,max = 19,message = "enter valid email")
     private String email;
     private String body;


}
