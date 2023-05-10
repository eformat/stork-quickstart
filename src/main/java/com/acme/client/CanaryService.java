package com.acme.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "stork://canary")
public interface CanaryService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String get();
}
