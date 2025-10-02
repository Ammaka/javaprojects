package Ammaka.java.courseinfo.repository;

import Ammaka.java.courseinfo.domain.Course;

import java.util.List;

public interface courserepository {
    void saveCourse(Course course);

     List<Course> getAllCourse();

     void addNotes(String id,String notes);

     static courserepository openCourseRepository (String databaseFile ){
         return new coursejdbcrepository(databaseFile);
     }

}
