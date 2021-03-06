package ru.orfac.mainconsumer.rest;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@Controller
public class RouteController {
  private final String MARINE_PRODUCER_APPLICATION_NAME = "MarineProducer";

  private final DiscoveryClient discoveryClient;

  public RouteController(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }

  @RequestMapping("/**")
  public ResponseEntity mirrorRest(
      @RequestBody(required = false) String body,
      HttpMethod method, HttpServletRequest request, HttpServletResponse response
  )
  throws URISyntaxException {
    String serviceUrl = serviceUrl();
    if (serviceUrl == null){
      return ResponseEntity.status(503).body("Service instance is not running");
    }
    URI uri = extractUri(request, serviceUrl);
    HttpHeaders headers = extractHeaders(request);

    HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    try {
      return restTemplate.exchange(uri, method, httpEntity, String.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getRawStatusCode())
          .headers(e.getResponseHeaders())
          .body(e.getResponseBodyAsString());
    }
  }

  private URI extractUri(final HttpServletRequest request, final String serviceUrl)
  throws URISyntaxException {
    String requestUrl = request.getRequestURI();
    URI uri = new URI(Objects.requireNonNull(serviceUrl));
    uri = UriComponentsBuilder.fromUri(uri)
        .path(requestUrl)
        .query(request.getQueryString())
        .build(true).toUri();
    return uri;
  }

  private String serviceUrl() {
    List<ServiceInstance> list = discoveryClient.getInstances(MARINE_PRODUCER_APPLICATION_NAME);
    if (list != null && list.size() > 0) {
      return list.get(0).getUri().toString();
    }
    return null;
  }

  private HttpHeaders extractHeaders(final HttpServletRequest request) {
    HttpHeaders headers = new HttpHeaders();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      headers.set(headerName, request.getHeader(headerName));
    }
    return headers;
  }
}
