package com.example;

import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.junit4.SpringRunner;
 
import com.brownfield.pss.fares.apigateway.FaresApiGateway;
 

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FaresApiGateway.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
