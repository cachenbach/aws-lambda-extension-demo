package ch.christian_achenba.extension.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequest {
    @JsonProperty
    private List<RegisterRequestEvent> events;

    @RegisterForReflection
    @ToString
    public static enum RegisterRequestEvent {
        @JsonProperty("INVOKE")
        INVOKE,
        @JsonProperty("SHUTDOWN")
        SHUTDOWN;
    }
}
