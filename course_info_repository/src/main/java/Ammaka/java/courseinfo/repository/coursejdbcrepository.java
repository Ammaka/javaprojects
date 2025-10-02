package Ammaka.java.courseinfo.repository;

import Ammaka.java.courseinfo.domain.Course;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class coursejdbcrepository implements courserepository{
    private static final String H2_DATABASE_URL = "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql'";
    private static final String INSERT_COURSE = """
            MERGE INTO Courses (id,name,length,url)
            VALUES (?,?,?,?)
            """;

    private static final String ADD_NOTES= """
            UPDATE Courses SET notes =?
            WHERE id=?
            """;
    private final DataSource dataSource;

    public coursejdbcrepository(String databaseFile){
        JdbcDataSource jdbcdatasource = new JdbcDataSource();
        jdbcdatasource.setURL(H2_DATABASE_URL.formatted(databaseFile));
        this.dataSource=jdbcdatasource;
    }


    @Override
    public void addNotes(String id, String notes){
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(ADD_NOTES);
            statement.setString(1,notes);
            statement.setString(2,id);
            statement.execute();
        } catch (SQLException e){
            throw new RepositoryException("failed to add notes to "+id,e);

        }
    }



    @Override
    public void saveCourse(Course course) {
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(INSERT_COURSE);
            statement.setString(1, course.id());
            statement.setString(2, course.name());
            statement.setLong(3, course.length());
            statement.setString(4, course.url());
            statement.execute();
        } catch (SQLException e){
            throw new RepositoryException("failed to save "+course,e);

        }
    }

    @Override
    public List<Course> getAllCourse() {
        try (Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM COURSES");

            List<Course> courses = new ArrayList<>();

            while(resultset.next()){
                Course course= new Course(resultset.getString(1),
                        resultset.getString(2),
                        resultset.getLong(3),
                        resultset.getString(4) ,
                        Optional.ofNullable(resultset.getString(5)));

                courses.add(course);
            }
            return Collections.unmodifiableList(courses);
        } catch (SQLException e){
            throw new RepositoryException("failed to retrieve courses",e);
        }
    }
}
