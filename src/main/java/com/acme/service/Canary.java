package com.acme.service;

import com.acme.client.CanaryService;
import io.smallrye.mutiny.Uni;
import io.smallrye.stork.Stork;
import io.smallrye.stork.api.Metadata;
import io.smallrye.stork.api.MetadataKey;
import io.smallrye.stork.api.Service;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.servicediscovery.dns.DnsMetadataKey;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.Set;

@Path("/")
public class Canary {

    private static final Logger log = Logger.getLogger(Canary.class);

    @RestClient
    CanaryService canaryService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String invoke() {
        if (log.isDebugEnabled()) {
            // Note: that calling getMetadata() will invoke SRV resolver
            // and for even number of records, the RoundRobin mechanism will return only half
            // RoundRobinLoadBalancer::select
            //   return instances.get(index.getAndIncrement() % instances.size());
            Stork stork = Stork.getInstance();
            Service service = stork.getService("canary");
            Uni<ServiceInstance> instance = service.selectInstance();
            Metadata<DnsMetadataKey> metadata = (Metadata<DnsMetadataKey>) instance.await().indefinitely().getMetadata();
            final Set<? extends MetadataKey> metadataKeys = metadata.getMetadata().keySet();
            metadataKeys.stream().forEach(k -> {
                log.infof("%s: %s", k, metadata.getMetadata().get(k));
            });
        }
        return canaryService.get();
    }
}
