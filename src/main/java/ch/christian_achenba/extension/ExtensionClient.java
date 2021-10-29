package ch.christian_achenba.extension;

import ch.christian_achenba.extension.request.RegisterRequest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.HeaderParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient()
@Produces("application/json")
public interface ExtensionClient {

    static final String LAMBDA_EXTENSION_IDENTIFIER = "Lambda-Extension-Identifier";
    static final String LAMBDA_EXTENSION_NAME = "Lambda-Extension-Name";

    @POST
    @Path("/register")
    Response registerExtension(@HeaderParam(LAMBDA_EXTENSION_NAME) String extensionName, RegisterRequest registerRequest);

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/event/next")
    Response getNext(@HeaderParam(LAMBDA_EXTENSION_IDENTIFIER) String extensionId);
}
