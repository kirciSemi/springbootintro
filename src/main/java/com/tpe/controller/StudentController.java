package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
        ***** SORU-1 :  @Controller yerine , @Component kullanirsam ne olur ??
        **    CEVAP-1 : Dispatcher , @Controller ile annote edilmis sınıfları tarar ve
        bunların içindeki @RequestMapping annotationlari algilamaya calisir. Dikkat :
        @Component ile annote edilen siniflar taranmayacaktir..

        Ayrica  @RequestMapping'i yalnızca sınıfları @Controller ile annote edilmis olan
        methodlar üzerinde/içinde kullanabiliriz ve @Component, @Service, @Repository vb.
        ile ÇALIŞMAZ…

        ***** SORU-2 : @RestController ile @Controller arasindaki fark nedir ??
        **   CEVAP-2 : @Controller, Spring MVC framework'ünün bir parçasıdır.genellikle HTML
        sayfalarının görüntülenmesi veya yönlendirilmesi gibi işlevleri gerçekleştirmek
        üzere kullanılır.
                       @RestController annotation'ı, @Controller'dan türetilmiştir ve RESTful
         web servisleri sağlamak için kullanılır.Bir sınıfın üzerine konulduğunda, tüm
         metodlarının HTTP taleplerine JSON gibi formatlarda cevap vermesini sağlar.

         ***** SORU-3 : Controller'dan direk Repo ya gecebilir miyim
         **   CEVAP-3: HAYIR, BusinessLogic ( kontrol ) katmani olan Service'i atlamamam gerekir.
 */


@RestController
@RequestMapping("/students")//http://localhost:8080/students
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    //  !!! Get All Students
    @GetMapping //end http://localhost:8080/students + GET
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students); //200 HTTP status code
        //return new ResponseEntity<>(students, HttpStatus.OK); ikisi aynı
    }

    // !!! Create New Students
    @PostMapping //end http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String,String>> createStudents(@Valid @RequestBody Student student){

        studentService.createStudent(student);
        Map<String,String> map = new HashMap<>();
        map.put("message","Student is creates successfully");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);//map + HTTP Status Kod
    }

    //!!!Not: getStudentById RequestParam
    @GetMapping("/query")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //!!!Not: getStudentById PathParam
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    //!!!Not: Delete student by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        String message ="Student is deleted successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
        //return ResponseEntity.ok(message) ile aynı işlemi yapar
    }

    //Update Students
    @PutMapping("/{id}") //end http://localhost:8080/students + PUT + JSON
    public ResponseEntity<String> udateStudent(@PathVariable("id") Long id,
                                               @Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id,studentDTO);
        String message = "Student is updated successfully.";
        return new ResponseEntity<>(message, HttpStatus.OK);//
    }

    //Not: getAllWithPage()

    @GetMapping("/page") //http://localhost:8080/students/page?page=0&size=2&sort=name&direction=ASC  + GET
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page,//kaçıncı sayfa gelsin
            @RequestParam("size") int size,//sayfa başı kaç eleman olsun
            @RequestParam("sort") String prop,//sıralama hangi değişkene göre yapılacak
            @RequestParam("direction") Sort.Direction direction //A-Z mi Z-A mı sıralanacak
            ){
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentPage = studentService.getAllWithPage(pageable);

        return ResponseEntity.ok(studentPage);
    }

    //Not: getByLastName()

    @GetMapping("/querylastname")//http://localhost:8080/students/querylastname + GET
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName){
        List<Student> list = studentService.findStudentByLastName(lastName);
        return ResponseEntity.ok(list);
    }

    //Not: getStudentByGrade(with JPQL Java Persietance Query Language)

    @GetMapping("/grade/{grade}") //http://localhost:8080/students/grade/70 + GET
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade){
        List<Student> list = studentService.getStudentsEqualsGrade(grade);
        return ResponseEntity.ok(list);
    }

    //Not: Logger için yazıldı
    @GetMapping("/welcome")//http://localhost:8080/students/welcome
    public String welcome(HttpServletRequest request){
        logger.warn("------------------ Welcome------------{}",request.getServletPath());
        return "Welcome to Student Controller";
    }













}
