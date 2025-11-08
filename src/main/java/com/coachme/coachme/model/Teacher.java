package com.coachme.coachme.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String bio;
    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }

    // ----------------------------
    // Many-to-Many mapping
    // ----------------------------
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "teacher_student_map",                        // join table name
            joinColumns = @JoinColumn(name = "teacher_id"),     // this entity's FK column
            inverseJoinColumns = @JoinColumn(name = "student_id") // other entity's FK
    )
    @JsonManagedReference           // prevents infinite recursion when serializing Teacher -> students
    private Set<Student> students = new HashSet<>();

    //constructors
    public Teacher() {
    }

    public Teacher(Long id, String name, String email, String password, String bio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
    }

    //getter and setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    // convenience helper to keep both sides in sync
    public void addStudent(Student s) {
        students.add(s);
        s.getTeachers().add(this);
    }

    public void removeStudent(Student s) {
        students.remove(s);
        s.getTeachers().remove(this);
    }
}
