package com.airhacks;

import models.JwtToken;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenNeededFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("AUTH FILTER");
        System.out.println("AUTH FILTER");
        System.out.println("AUTH FILTER");

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        JwtToken jwtToken = new JwtToken(token);
        if(jwtToken.VefifyToken()){
            System.out.println("valid token: " + token);
        }else{
            System.out.println("Invaldig token: " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
