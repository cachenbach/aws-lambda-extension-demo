package ch.christian_achenba.extension.events;

import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@RegisterForReflection
@ToString
public class EventShutdown extends ExtensionEvent {

    private ShutdownReason shutdownReason;

    @AllArgsConstructor
    @RegisterForReflection
    @ToString
    static enum ShutdownReason {
        SPINDOWN("spindown"),
        TIMEOUT("timeout"),
        FAILURE("failure");

        @JsonValue
        @Getter
        private final String value;
    }
}
