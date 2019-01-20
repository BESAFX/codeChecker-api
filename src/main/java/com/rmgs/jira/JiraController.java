package com.rmgs.jira;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api/jira")
public class JiraController {

    @Value("${jira.api.http.host}")
    private String jiraApiUrl;


    private RestTemplate restTemplate;

    public JiraController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @CrossOrigin
    @GetMapping("/bugs")
    public ResponseEntity<Object> getAllBugs() {
        RestTemplate restTemplate = new RestTemplate();
        String plainCreds = "ehab.alagoza:Ihabfcis1";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<Object> responseEntity = null;
        try {
            String url = jiraApiUrl + "/rest/api/latest/search?jql=project=COD and issuetype = Bug";
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<Object>(responseEntity.getBody(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        RestTemplate restTemplate = new RestTemplate();
        String plainCreds = "ehab.alagoza:Ihabfcis1";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<Object> responseEntity = null;
        try {
            String url = jiraApiUrl + "/rest/api/latest/user/search?startAt=0&maxResults=1000&username=.";
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<Object>(responseEntity.getBody(), HttpStatus.OK);
    }


}
