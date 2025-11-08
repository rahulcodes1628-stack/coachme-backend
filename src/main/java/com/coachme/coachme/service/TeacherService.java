package com.coachme.coachme.service;

import com.coachme.coachme.model.Student;
import com.coachme.coachme.model.Teacher;
import com.coachme.coachme.repository.StudentRepository;
import com.coachme.coachme.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Teacher addTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));
    }


    @Transactional
    public Teacher enrollStudentInTeacher(Long teacherId, Long studentId) {
        Optional<Teacher> optT = teacherRepository.findById(teacherId);
        Optional<Student> optS = studentRepository.findById(studentId);

        if (optT.isEmpty()) {
            throw new IllegalArgumentException("Teacher not found with id: " + teacherId);
        }
        if (optS.isEmpty()) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }

        Teacher teacher = optT.get();
        Student student = optS.get();

        // keep both sides in sync using helper
        teacher.addStudent(student);

        // saving teacher is enough (cascade MERGE/PERSIST handles link)
        return teacherRepository.save(teacher);
    }
}
