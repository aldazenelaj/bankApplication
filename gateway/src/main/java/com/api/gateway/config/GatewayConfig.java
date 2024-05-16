package com.api.gateway.config;


import com.api.gateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up API Gateway routes and filters.
 */
@Configuration
public class GatewayConfig {


    @Autowired
    AuthFilter authFilter;

    /**
     * Defines custom routes for the API Gateway.
     *
     * @param builder the RouteLocatorBuilder used to build the routes
     * @return a RouteLocator containing the defined routes
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("usermanagement", r -> r.path("/usermanagement/**")//.and().header("")
                        .and().method("GET").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8484"))
                .route("usermanagement", r -> r.path("/usermanagement/**")//.and().header("")
                        .and().method("POST").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8484"))
                .route("usermanagement", r -> r.path("/usermanagement/**")//.and().header("")
                        .and().method("DELETE").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8484"))
                .route("usermanagement", r -> r.path("/usermanagement/**")//.and().header("")
                        .and().method("PUT").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8484"))
                .route("authentication", r -> r.path("/auth/login")
                        .uri("http://localhost:8081"))
                .route("authentication", r -> r.path("/auth/user")
                        .and().method("POST").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8081"))
                .route("authentication", r -> r.path("/auth/user")
                        .and().method("DELETE").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8081"))
                .route("transaction", r -> r.path("/transaction/**")
                        .and().method("POST").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:8082"))
                .build();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
