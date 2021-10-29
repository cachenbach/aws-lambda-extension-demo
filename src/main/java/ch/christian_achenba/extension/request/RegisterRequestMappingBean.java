package ch.christian_achenba.extension.request;

import io.quarkus.arc.DefaultBean;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
public class RegisterRequestMappingBean {

    @Inject
    RegisterRequestMapping registerRequestMapping;

    @Produces
    @DefaultBean
    public RegisterRequest registerRequest(){
        final List<RegisterRequest.RegisterRequestEvent> registerRequestEvents = registerRequestMapping.events().stream().map(RegisterRequest.RegisterRequestEvent::valueOf).collect(Collectors.toList());
        return RegisterRequest.builder()
                    .events(registerRequestEvents)
                    .build();
    }
}
