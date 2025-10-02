package Ammaka.java.courseinfo.cli.service;

import Ammaka.java.courseinfo.domain.Course;
import Ammaka.java.courseinfo.repository.courserepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class courseStorageServiceTest {

    @Test
    void storePluralSightCourse() {
        courserepository repository= new inmemoryCourseRepo();
        courseStorageService coursestorageservice= new courseStorageService(repository);
        pluralsightcourse ps1= new pluralsightcourse("1","title1","01:40:00.123","/url-1",false);
        coursestorageservice.StorePluralSightCourse(List.of(ps1));

        Course expected = new Course("1","title1",100,"https://app.pluralsight.com/url-1", Optional.empty());
        assertEquals(List.of(expected),repository.getAllCourse());
    }

    static class inmemoryCourseRepo implements courserepository{
        private final List<Course> courses =new ArrayList<>();
        @Override
        public void saveCourse(Course course) {
            courses.add(course);
        }

        @Override
        public List<Course> getAllCourse() {
            return courses;
        }

        @Override
        public  void addNotes(String id, String notes){
            throw new UnsupportedOperationException();
        }
    }
}