package Ammaka.java.courseinfo.server;

import Ammaka.java.courseinfo.domain.Course;
import Ammaka.java.courseinfo.repository.RepositoryException;
import Ammaka.java.courseinfo.repository.courserepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/courses")
public class CourseResource {
    private static final Logger LOG = LoggerFactory.getLogger(CourseResource.class);

    private final courserepository courserepository;

    public CourseResource(courserepository courserepository) {
        this.courserepository = courserepository;
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Stream<Course> getCourses(){
        try {
            return courserepository
                    .getAllCourse()
                    .stream()
                    .sorted(Comparator.comparing(Course::id));
        }catch(RepositoryException e){
            LOG.error("could not retrieve courses from the database",e);
            throw new NotFoundException();
        }

    }

    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addNotes(@PathParam ("id")String id, String notes){
       courserepository.addNotes(id,notes);
    }
}
