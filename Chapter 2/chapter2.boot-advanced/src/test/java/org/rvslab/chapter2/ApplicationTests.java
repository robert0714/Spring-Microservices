package org.rvslab.chapter2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rvslab.chapter2.Application;
import org.rvslab.chapter2.Greet;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity; 
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
 
@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 
public class ApplicationTests {

	private final TestRestTemplate restTemplate = new TestRestTemplate();
    //Required to Generate JSON content from Java objects
	
    @LocalServerPort
    private int port ;
	
    @Test(expected=java.lang.AssertionError.class)
	public void testVanillaService() { 
	    Greet greet = restTemplate.getForObject("http://localhost:" + port+"/", Greet.class);
	    Assert.assertEquals("Hello World!", greet.getMessage());
	} 

	@Test(expected=java.lang.AssertionError.class)
	public void testSecureService() {	
		String plainCreds = "guest:guest123";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(Base64.encode(plainCreds.getBytes())));
		HttpEntity<String> request = new HttpEntity<String>(headers); 
		
		String url = "http://localhost:" + port ;
		ResponseEntity<Greet> response = restTemplate.exchange(url, HttpMethod.GET, request, Greet.class);
		Assert.assertEquals("Hello World!", response.getBody().getMessage());
	}
	

	@Test
	public void testOAuthService() {	
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setUsername("guest");
        resource.setPassword("guest123");
        String url = "http://localhost:" + port ;
        resource.setAccessTokenUri(url+"/oauth/token" );
        resource.setClientId("trustedclient");
        resource.setClientSecret("trustedclient123");
        resource.setGrantType("password");
  
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, clientContext);
 
        Greet greet = restTemplate.getForObject(url, Greet.class);

        Assert.assertEquals("Hello World!", greet.getMessage());
	}
	
	
}