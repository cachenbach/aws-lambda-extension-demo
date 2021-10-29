package ch.christian_achenba.extension.events;

import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@RegisterForReflection
@NoArgsConstructor
@ToString
public class EventInvoke extends ExtensionEvent {
    private String description;
    private String invokedFunctionArn;

    @RegisterForReflection
    @NoArgsConstructor
    @ToString
    private static class XRayTracingInfo {
        private Type type;
        private String value;

        @AllArgsConstructor
        @RegisterForReflection
        private static enum Type {
            X_AMZN_TRACE_ID("X-Amzn-Trace-Id");

            @JsonValue
            @Getter
            private final String value;
        }
    }
}
