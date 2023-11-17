package com.tpe.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Getter
    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be white space")
    @Size(min=2,max = 25,message = "First name '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false,length = 25)
    /*final*/ private String name;

    @Column(nullable = false,length = 25)
    /*final*/ private String lastName;

    //@Column optional
    /*final*/ private Integer grade;

    @Column(nullable = false,length = 50,unique = true)
    @Email(message = "Provide valid email")
    /*final*/ private String email;//xxx@yyyy.com

    /*final*/ private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss",timezone = "Turkey")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();


//    //Getter - Setter
//
//
//    public long getId() {
//        return id;
//    }
//
////    public void setId(long id) {
////        this.id = id;
////    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public Integer getGrade() {
//        return grade;
//    }
//
//    public void setGrade(Integer grade) {
//        this.grade = grade;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public LocalDateTime getCreateDate() {
//        return createDate;
//    }
//
////    public void setCreateDate(LocalDateTime createDate) {
////        this.createDate = createDate;
////    }
//
//
//    public Student() {
//    }
//
//    public Student(String name, String lastName, Integer grade, String email, String phoneNumber) {
//        this.name = name;
//        this.lastName = lastName;
//        this.grade = grade;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }
}
