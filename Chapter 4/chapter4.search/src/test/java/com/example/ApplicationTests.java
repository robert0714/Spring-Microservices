package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith; 
import com.brownfield.pss.search.Application;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.context.embedded.LocalServerPort; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ) 
public class ApplicationTests {
	private final TestRestTemplate restTemplate = new TestRestTemplate();
    //Required to Generate JSON content from Java objects
	
    @LocalServerPort
    private int port ;
    
    /**
    *
    */
   public static final ObjectMapper objectMapper = new ObjectMapper();
    
//    @Test
//    public void testGetById() {
//        //API call
//        Map<String, Object> response
//                = restTemplate.postForObject("http://localhost:" + port + "/search/get/", Map.class);
//
//        assertNotNull(response);
//
//        //Asserting API Response
//        String id = response.get("id").toString();
//        assertNotNull(id);
//        assertEquals("1", id);
//        String name = response.get("name").toString();
//        assertNotNull(name);
//        assertEquals("Booking 1", name);
//        boolean isModified = (boolean) response.get("isModified");
//        assertEquals(false, isModified);
//    }
}
