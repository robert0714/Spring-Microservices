package com.brownfield.pss.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
 
import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication
@EnableDiscoveryClient 
public class Application implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@LoadBalanced 
	@Autowired
	RestTemplate searchClient;
	
	@LoadBalanced
	@Autowired
	RestTemplate bookingClient;
	
	@LoadBalanced
	@Autowired
	RestTemplate checkInClient;
	
	//RestTemplate restClient= new RestTemplate();
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... strings) throws Exception {
		//Search for a flight
		SearchQuery searchQuery = new SearchQuery("NYC","SFO","22-JAN-16");
 		//Flight[] flights = searchClient.postForObject("http://search-service/search/get", searchQuery, Flight[].class); 
		Flight[] flights = searchClient.postForObject("http://search-apigateway/api/search/get", searchQuery, Flight[].class); 
 		
		Arrays.asList(flights).forEach(flight -> logger.info(" flight >"+ flight));
  		
		//create a booking only if there are flights.
 		if(flights == null || flights.length == 0){
 			return;
 		}
		Flight flight = flights[0];
		BookingRecord booking = new BookingRecord(flight.getFlightNumber(),flight.getOrigin(),
												  flight.getDestination(), flight.getFlightDate(),null,
												  flight.getFares().getFare());
		Set<Passenger> passengers = new HashSet<Passenger>();
		passengers.add(new Passenger("Gavin","Franc","Male", booking));
		booking.setPassengers(passengers);
		long bookingId =0;
		try { 
			//long bookingId = bookingClient.postForObject("http://book-service/booking/create", booking, long.class); 
			 bookingId = bookingClient.postForObject("http://book-apigateway/api/booking/create", booking, long.class); 
			logger.info("Booking created "+ bookingId);
		}catch (Exception e){
			logger.error("BOOKING SERVICE NOT AVAILABLE...!!!");
		}
		
		//check in passenger
		if(bookingId == 0) return;
		try {
			CheckInRecord checkIn = new CheckInRecord("Franc", "Gavin", "28C", null, "BF101","22-JAN-16", bookingId);
			long checkinId = checkInClient.postForObject("http://checkin-apigateway/api/checkin/create", checkIn, long.class); 
			logger.info("Checked IN "+ checkinId);
		}catch (Exception e){
			logger.error("CHECK IN SERVICE NOT AVAILABLE...!!!"); 
		}
	}
		
}


@Configuration
class EurekaConfig { 

	private static final Logger logger = LoggerFactory.getLogger(EurekaConfig.class);
	
    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfigBean() {
    	final EurekaInstanceConfigBean config = 
				new EurekaInstanceConfigBean (new InetUtils(new InetUtilsProperties()));
    	try { 
	   		logger.info("Ereka Pre Configuring-3");
		   AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		    config.setDataCenterInfo(info);
		    info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(), info.get(AmazonInfo.MetaDataKey.publicIpv4));
		    config.setHostname(info.get(AmazonInfo.MetaDataKey.localHostname));
		    
		    logger.info("hostname" + info.get(AmazonInfo.MetaDataKey.localHostname));
		    logger.info("IP" + info.get(AmazonInfo.MetaDataKey.publicIpv4));
		    
		//    config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4)); 
		   
	   		config.setNonSecurePortEnabled(true);
	        config.setNonSecurePort(0); //change this later
	    //    config.setPreferIpAddress(true);
	        
	       // config.setIpAddress("54.85.107.37");
	        config.getMetadataMap().put("instanceId",  info.get(AmazonInfo.MetaDataKey.localHostname));
	 
		   // logger.info("info" + info); 
    	}catch (Exception e){
    		e.printStackTrace();
    	}
	    return config;
	}
}