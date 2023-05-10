# stork-quickstart

Use DNS SRV for globally load balancing REST calls to the OpenShift canary service.

See:
- https://github.com/smallrye/smallrye-stork/pull/549

```bash
dig SRV canary.demo.redhatlabs.dev
```

See the [application.properties](src/main/resources/application.properties) file for configuration.

The rest interface service is configured using the service name.

```java
@RegisterRestClient(baseUri = "stork://canary")
public interface CanaryService
```

## Run

Client

```bash
mvn quarkus:dev
```

Test

```bash
curl localhost:8080
```
