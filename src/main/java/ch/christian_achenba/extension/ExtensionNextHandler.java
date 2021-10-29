package ch.christian_achenba.extension;

import ch.christian_achenba.extension.events.EventInvoke;
import ch.christian_achenba.extension.events.EventShutdown;
import ch.christian_achenba.extension.events.ExtensionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ExtensionNextHandler implements Runnable {

    private final AtomicBoolean run = new AtomicBoolean(true);
    private final ExtensionApiService extensionApiService;
    private final ExtensionClient extensionClient;
    private final String lambdaExtensionIdentifier;
    private final ObjectMapper objectMapper;

    ExtensionNextHandler(final ExtensionApiService extensionApiService,
                         final ExtensionClient extensionClient,
                         final String lambdaExtensionIdentifier,
                         final ObjectMapper objectMapper) {
        this.extensionApiService = extensionApiService;
        this.extensionClient = extensionClient;
        this.lambdaExtensionIdentifier = lambdaExtensionIdentifier;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {
        while (run.get()) {
            try {
                final Response response = extensionClient.getNext(lambdaExtensionIdentifier);
                log.info("Content-Type of Next Response: " + response.getHeaderString("Content-Type"));
                final String content = response.readEntity(String.class);
                final ExtensionEvent extensionEvent = objectMapper.readValue(content, ExtensionEvent.class);
                switch (extensionEvent.getEventType()) {
                    case INVOKE:
                        final EventInvoke eventInvoke = (EventInvoke) extensionEvent;
                        handleInvoke(eventInvoke);
                        break;
                    case SHUTDOWN:
                        final EventShutdown eventShutdown = (EventShutdown) extensionEvent;
                        handleShutDown(eventShutdown);
                        break;
                    default:
                        log.error("Invalid event type received : " + extensionEvent.getEventType());
                }
            } catch (ProcessingException e) {
                final String entity = ((ProcessingException) e).getMessage();
                log.error("Next Response: " + entity, e);
            } catch (Exception e) {
                log.error("Error class:" + e.getClass().getName());
                log.error("Error while processing extension -" + e.getMessage(), e);
            }
        }
    }


    private void handleShutDown(EventShutdown eventShutdown) {
        log.info("Received SHUTDOWN Event");
        run.set(false);
        extensionApiService.shutdown();
        System.exit(0);
    }

    public static void handleInvoke(final EventInvoke eventInvoke) {
        log.info("Received INVOKE Event");
    }
}
