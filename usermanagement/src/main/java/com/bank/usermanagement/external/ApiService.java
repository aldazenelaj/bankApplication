package com.bank.usermanagement.external;

import com.bank.usermanagement.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Service class for interacting with external authentication services.
 */
@Service
public class ApiService {
    private static final Logger log = LoggerFactory.getLogger(ApiService.class.getName());

    @Autowired
    private Environment environment;

    @Autowired

    public ApiService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Calls the external authentication service to create a user using RestTemplate.
     *
     * @param userDto the user data transfer object
     * @param token   the security token used for authentication
     * @return true if the user was created successfully, false otherwise
     */
    public boolean callUserPostAuthenticationServiceRest(UserDto userDto, String token) {
        log.info("Calling authenticating service to create user with username: " + userDto.getUsername());

        RestTemplate restTemplate = new RestTemplate();
        String url = environment.getProperty("authentication.service.base.url") + environment.getProperty("authentication.service.user.url");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getStatusCode() == HttpStatus.OK;
    }

    /**
     * Calls the external authentication service to delete a user using RestTemplate.
     *
     * @param username the username of the user to be deleted
     * @param token    the security token used for authentication
     * @return true if the user was deleted successfully, false otherwise
     */
    public boolean callUserDeleteAuthenticationServiceRest(String username, String token) {
        try {
            log.info("Calling authentication service to delete user with username: " + username);

            RestTemplate restTemplate = new RestTemplate();
            String baseUrl = environment.getProperty("authentication.service.base.url");
            String userUrl = environment.getProperty("authentication.service.user.url");
            String url = baseUrl + userUrl;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<UserDto> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );

            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("No user found with username: " + username);
                return false;
            } else {
                log.error("Error during deletion of user: " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            log.error("Unexpected error during deletion of user: " + e.getMessage());
            throw new RuntimeException("Server error during user deletion", e);
        }
    }


}
