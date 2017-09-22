package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@SpringBootApplication
public class Main {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  private static final String DESTINATION = "destination";
  private RestTemplate restTemplate = new RestTemplate();

  @GetMapping("/")
  public String hello() {
    return "Hello World!";
  }

  @PostMapping(path = "/request", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> notify(@RequestHeader(DESTINATION) String url, @RequestBody String body, @RequestHeader MultiValueMap<String, String> headers) {
    LOGGER.info("Incoming url {} with body {}", url, body);
    MultiValueMap<String, String> newHeaders = new LinkedMultiValueMap<>(headers);
    newHeaders.remove(DESTINATION);
    HttpEntity<String> entity = new HttpEntity<>(body, newHeaders);

    return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
  }


  @RequestMapping("/log")
  public void log(HttpServletRequest request, @RequestBody(required = false) String body, @RequestHeader MultiValueMap<String, String> headers, @RequestParam(required = false) Map<String,String> requestParams) {
    requestParams = requestParams == null ? Collections.emptyMap() : requestParams;
    headers = headers == null ? new LinkedMultiValueMap<>() : headers;
    StringBuilder requestParamsString = new StringBuilder();
    requestParams.forEach((k,v) -> requestParamsString.append(k).append("=").append(v).append("&")) ;
    StringBuilder headersString = new StringBuilder();
    headers.forEach((k,v) -> headersString.append(k).append("=").append(v).append(", "));
    LOGGER.info("LOG incoming method {} params {} body {} headers {}", request.getMethod(), requestParamsString.toString(), body, headersString);
  }

}
