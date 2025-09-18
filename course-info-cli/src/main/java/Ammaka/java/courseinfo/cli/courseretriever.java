package Ammaka.java.courseinfo.cli;

import Ammaka.java.courseinfo.cli.service.CourseRetrievalService;
import Ammaka.java.courseinfo.cli.service.pluralsightcourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.function.Predicate.not;

public class courseretriever {

    private static final Logger LOG = LoggerFactory.getLogger(courseretriever.class);
    public static void main (String[]args){
        LOG.info("starting course retriever...");

        if(args.length==0){
            LOG.warn("please enter the author of the course");
            return;
        }

        try {
            retrieveCourses(args[0]);

        }catch(Exception e){
            LOG.error("unexpected error",e);
        }
    }
    private static void retrieveCourses(String authorId) {
        LOG.info("retrieving courses for '{}' ", authorId);

        CourseRetrievalService courseretrievalservice=new CourseRetrievalService();
        List<pluralsightcourse> coursesToStore= courseretrievalservice.getCourseFor(authorId)
                        .stream()
                        .filter(not(pluralsightcourse::isRetired))
                                .toList();

        LOG.info("retrieved the following {} courses {}",coursesToStore.size(),coursesToStore);
    }
}
