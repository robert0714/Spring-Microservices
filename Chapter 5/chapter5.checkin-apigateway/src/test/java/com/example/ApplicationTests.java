package com.example;

import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.junit4.SpringRunner;

import com.brownfield.pss.checkin.apigateway.CheckinApiGateway;
 
 

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CheckinApiGateway.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
