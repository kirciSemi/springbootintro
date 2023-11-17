package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName")//!!!Sadece JSON çıktıda name field yerine bookName olarak gösterilmesini sağlar
    private String name;

    @ManyToOne
    @JsonIgnore//StackOverFlow önlemek için eklendi
    @JoinColumn(name="student_id")//Default isimde student_id olurdu ama biz atadık
    private Student student;
}
