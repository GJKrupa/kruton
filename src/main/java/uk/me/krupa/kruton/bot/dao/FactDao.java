package uk.me.krupa.kruton.bot.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FactDao {

    @Value("${kruton.service.fact}")
    private String factServiceEndpoint;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String getFactFor(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(factServiceEndpoint + "/api/fact/" + name, String.class);
        return response;
    }

    public void setFact(String name, String definition) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(
                factServiceEndpoint + "/api/fact/" + name, definition, String.class);
    }

}
