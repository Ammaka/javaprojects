package Ammaka.java.courseinfo.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetrievalService {

    private static final String PS_URI ="https://app.pluralsight.com/profile/data/author/%s/all-content";
    private static final HttpClient CLIENT  = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<pluralsightcourse> getCourseFor(String authorId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PS_URI.formatted(authorId)))
                .GET()
                .build();//define the request variable



        try {  //catch errors with a try catch block
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());


            return switch(response.statusCode()) {
                case 200->  toPluralsightcourses(response);

                case 404->List.of();
                default -> throw new RuntimeException("Api call failed");
            };// send request to client ...

        }catch(IOException | InterruptedException e){
            throw new RuntimeException(("could not call the API"));
        }


    }

    private static List<pluralsightcourse> toPluralsightcourses(HttpResponse<String> response) throws JsonProcessingException {
        JavaType returntype = OBJECT_MAPPER.getTypeFactory()
        .constructCollectionType(List.class, pluralsightcourse.class);

        return OBJECT_MAPPER.readValue(response.body(), returntype);
    }

}
