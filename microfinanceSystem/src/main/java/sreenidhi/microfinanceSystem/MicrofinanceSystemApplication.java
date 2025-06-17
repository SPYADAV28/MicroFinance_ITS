package sreenidhi.microfinanceSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class MicrofinanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrofinanceSystemApplication.class, args);
	}
	
	   @Bean
	    public CommonsRequestLoggingFilter logFilter() {
	        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
	        filter.setIncludeQueryString(true);       // Log query parameters
	        filter.setIncludePayload(true);           // Log request body (up to max length)
	        filter.setMaxPayloadLength(10000);        // Max length of body to log
	        filter.setIncludeHeaders(false);          // Optional: include headers or not
	        filter.setAfterMessagePrefix("REQUEST DATA : ");
	        return filter;
	    }
	   
	   @Bean
	   public ObjectMapper objectMapper() {
	       ObjectMapper mapper = new ObjectMapper();
	       mapper.registerModule(new JavaTimeModule());
	       mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // optional: better formatting
	       return mapper;
	   }

}
