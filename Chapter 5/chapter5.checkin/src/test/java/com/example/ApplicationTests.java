package com.example;

import org.junit.Test;
import org.junit.runner.RunWith; 

import com.brownfield.pss.checkin.Application;

import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
