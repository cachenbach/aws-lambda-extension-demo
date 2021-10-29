package ch.christian_achenba.extension;

import ch.christian_achenba.extension.request.RegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
public class ExtensionApiService {
    public static final String LAMBDA_EXTENSION_IDENTIFIER = "lambda-extension-identifier";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ExtensionClient extensionClient;
    private final String extensionName;
    private final ObjectMapper objectMapper;
    private final RegisterRequest registerRequest;

    private ExtensionNextHandler extensionNextHandler;

    @Inject
    public ExtensionApiService(@RestClient final ExtensionClient extensionClient,
                               @ConfigProperty(name = "extensionName") final String extensionName,
                               final RegisterRequest registerRequest,
                               final ObjectMapper objectMapper) {
        this.extensionClient = extensionClient;
        this.extensionName = extensionName;
        this.registerRequest = registerRequest;
        this.objectMapper = objectMapper;
    }

    public String register() throws JsonProcessingException {
        log.info("Register Extension");
        try {
            log.debug("Register Request: " + objectMapper.writeValueAsString(registerRequest));
            final Response extension = extensionClient.registerExtension(extensionName, registerRequest);
            final String lambdaExtensionIdentifier = extension.getHeaderString(LAMBDA_EXTENSION_IDENTIFIER);
            log.debug(LAMBDA_EXTENSION_IDENTIFIER + ": " + lambdaExtensionIdentifier);
            extensionNextHandler = new ExtensionNextHandler(this, extensionClient, lambdaExtensionIdentifier, objectMapper);
            executorService.submit(extensionNextHandler);
            return lambdaExtensionIdentifier;
        } catch (WebApplicationException e) {
            final String entity = ((WebApplicationException) e).getResponse().readEntity(String.class);
            log.error("Register Response: " + entity, e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
