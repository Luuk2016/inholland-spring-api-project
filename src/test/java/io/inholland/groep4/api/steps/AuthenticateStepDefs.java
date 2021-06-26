package io.inholland.groep4.api.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.inholland.groep4.api.controller.SpringIntegrationTest;
import io.inholland.groep4.api.model.DTO.LoginDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class AuthenticateStepDefs extends SpringIntegrationTest {

    RestTemplate template = new RestTemplate();
    ResponseEntity<String> responseEntity;
    String response;

    HttpHeaders headers = new HttpHeaders();
    String baseUrl = "http://localhost:8080/api/authenticate";

    @When("^the client posts their credentials to /authenticate$")
    public void theClientPostsAuthentication() throws JsonProcessingException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        LoginDTO dto = new LoginDTO();
        dto.setPassword("test");
        dto.setUsername("john");
        URI uri = new URI(baseUrl);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(dto), headers);
        responseEntity = template.postForEntity(uri, entity, String.class);
    }

    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCodeOf(int arg0) {
    }
}