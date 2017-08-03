package org.rvslab.chapter1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;  
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate; 
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

 
@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
public class ApplicationTests {

	private final TestRestTemplate restTemplate = new TestRestTemplate();
    //Required to Generate JSON content from Java objects
	
    @LocalServerPort
    private int port ;
	
    @Test 
	public void testHelloWorld() { 
	    Greet greet = restTemplate.getForObject("http://localhost:" + port+"/greet", Greet.class);
	    Assert.assertEquals("Hello World!", greet.getMessage());
	} 
    @Test 
	public void testHelloHATEOAS() {	
    	Greet greet = restTemplate.getForObject("http://localhost:" + port+"/greeting", Greet.class);
	    Assert.assertEquals("Hello HATEOAS", greet.getMessage());
	}
}
