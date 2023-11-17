package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentRepository studentRepository;


    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    // !!! Create New Students
    public void createStudent(Student student) {
        // !!! email unique mi kontrolu
        if(studentRepository.existsByEmail(student.getEmail())){
            logger.warn("Email is already exist.");
            throw new ConflictException("Email is already exist.");
        }
        studentRepository.save(student);
    }

    //!!!Not: getStudentById
    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(()->
            new ResourceNotFoundException("Student not found with id: "+id));
    }

    public void deleteStudent(Long id) {
        //!!! Acaba yukarıda verilen idli öğrenci db' de var mı
        Student student = findStudent(id);
        studentRepository.delete(student);
    }

    //Update Students
    public void updateStudent(Long id, StudentDTO studentDTO) {
        // id' li öğrenci var mı?
        Student student = findStudent(id);
        //email unique mi?
        boolean emailExist = studentRepository.existsByEmail(studentDTO.getEmail());
        if(emailExist && !studentDTO.getEmail().equals((student.getEmail()))){
            throw new ConflictException("Email is already exist");
        }
        /*
               1) kendi email mrc, yenisindede mrc gir --> ( UPDATE OLUR )
               2) kendi email mrc, ahmet girdi ( DB de zaten var) --> ( CONFLICT )
               3) kendi email mrc, mhmet girdi ( DB de YOk ) --> ( UPDATE OLUR )
         */

        // !!! DTO --> POJO
        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        studentRepository.save(student);
    }
    //Not: getAllWithPage()
    public Page<Student> getAllWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    //Not: getByLastName()

    public List<Student> findStudentByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    //Not: getStudentByGrade(with JPQL Java Persietance Query Language)
    public List<Student> getStudentsEqualsGrade(Integer grade) {
        return studentRepository.findAllEqualsGrade(grade);
    }
}
