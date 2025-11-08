package com.coachme.coachme.controller;

import com.coachme.coachme.model.Teacher;
import com.coachme.coachme.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher){
        return teacherService.addTeacher(teacher);
    }

    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    // Enroll a student to a teacher
    @PostMapping("/{teacherId}/students/{studentId}")
    public Teacher enrollStudent(@PathVariable Long teacherId, @PathVariable Long studentId) {
        return teacherService.enrollStudentInTeacher(teacherId, studentId);
    }

    // Get students for a teacher
    @GetMapping("/{teacherId}/students")
    public Object getStudentsOfTeacher(@PathVariable Long teacherId) {
        Teacher t = teacherService.getTeacherById(teacherId);
        // return only the students set (Teacher has @JsonManagedReference so it will serialize students)
        return t.getStudents();
    }
}
