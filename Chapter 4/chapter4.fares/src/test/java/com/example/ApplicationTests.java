package com.example;

import org.junit.Test;
import org.junit.runner.RunWith; 

import com.brownfield.pss.fares.Application;

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
    
	@Test
	public void tesatContextLoads() {
	}

}
