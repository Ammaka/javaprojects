package Ammaka.java.courseinfo.cli.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class pluralsightcourseTest {

    @ParameterizedTest
    @CsvSource(textBlock= """
               01:08:54.9613330,68
               00:05:37,5
               00:00:00,0
               """)
    void durationinminutes(String input, long expected) {
        pluralsightcourse course = new pluralsightcourse("id","test course","00:05:37","url",false);
        assertEquals(5,course.durationinminutes());
    }

}