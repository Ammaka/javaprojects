package Ammaka.java.courseinfo.cli.service;

import Ammaka.java.courseinfo.domain.Course;
import Ammaka.java.courseinfo.repository.courserepository;

import java.util.List;
import java.util.Optional;

public class courseStorageService {


    private static final String PS_BASE_URL ="https://app.pluralsight.com" ;
    private final courserepository courseRepository;


    public courseStorageService(courserepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public void StorePluralSightCourse (List<pluralsightcourse> pscourses){
        for (pluralsightcourse pscourse : pscourses){
            Course course = new Course (pscourse.id(),
                    pscourse.title(),pscourse.durationinminutes(),
                    PS_BASE_URL+pscourse.contentUrl(), Optional.empty());
            courseRepository.saveCourse(course);
        }

    }
}
