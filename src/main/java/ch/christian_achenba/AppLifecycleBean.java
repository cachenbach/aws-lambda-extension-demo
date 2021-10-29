package ch.christian_achenba;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import ch.christian_achenba.extension.ExtensionApiService;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    ExtensionApiService extensionApiService;

    void onStart(@Observes StartupEvent ev) throws JsonProcessingException {
        log.info("Start Extension Log Shipper");
        final String lambdaExtensionIdentifier = extensionApiService.register();
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("Stop Extension Log Shipper");
    }

}
