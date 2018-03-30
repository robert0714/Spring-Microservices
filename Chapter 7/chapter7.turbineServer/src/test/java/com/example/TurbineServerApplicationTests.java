package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.netflix.discovery.shared.Application;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest (classes=Application.class)
@WebAppConfiguration
public class TurbineServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
