package com.acme.client;

import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestContext;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestFilter;

@Provider
public class CanaryFilter implements ResteasyReactiveClientRequestFilter {

    private static final Logger log = Logger.getLogger(CanaryFilter.class);

    @Override
    public void filter(ResteasyReactiveClientRequestContext requestContext) {
        //requestContext.getHeaders().add("Host", "canary-openshift-ingress-canary");
        log.infof("Resolved address by Stork: %s",requestContext.getUri().toString());
    }

}
