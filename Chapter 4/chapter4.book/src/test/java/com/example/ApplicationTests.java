package com.example;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.brownfield.pss.book.Application;
import com.brownfield.pss.book.component.BookingComponent;
import com.brownfield.pss.book.component.Fare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.embedded.LocalServerPort; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ) 
public class ApplicationTests {
	private final TestRestTemplate restTemplate = new TestRestTemplate();
    //Required to Generate JSON content from Java objects
	
    @LocalServerPort
    private int port ;    
    
    @Autowired
    private BookingComponent bookComponent ;
    
    @Before
    public void setup() {    	 
    	Fare fare =  mock(Fare.class);
    	when(fare.getFare()).thenReturn("101");
    	when(fare.getFlightDate()).thenReturn("22-JAN-16");
    	when(fare.getFlightNumber()).thenReturn("BF101"); 
    	
    	String url ="http://localhost:8080/fares/get?flightNumber=BF101&flightDate=22-JAN-16"; 
    	
		when(this.restTemplate.getForObject(Mockito.anyString(), Fare.class)).thenReturn(fare);
		
		ReflectionTestUtils.setField(bookComponent, "restTemplate", this.restTemplate);
    	
    }
    @Test
    public void testGetById() {
        //API call
        Map<String, Object> response
                = restTemplate.getForObject("http://localhost:" + port + "/booking/get/1", Map.class);

        assertNotNull(response);

        //Asserting API Response
        String id = response.get("id").toString();
        assertNotNull(id);
        assertEquals("1", id);
        String name = response.get("name").toString();
        assertNotNull(name);
        assertEquals("Booking 1", name);
        boolean isModified = (boolean) response.get("isModified");
        assertEquals(false, isModified);
    }

    /**
     * Test the GET /v1/booking/create API
     */
	@Test
	public void testCreate() {
		
	}

}
