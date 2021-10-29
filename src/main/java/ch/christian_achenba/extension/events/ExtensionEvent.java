package ch.christian_achenba.extension.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventInvoke.class, name = ExtensionEvent.Type.Constants.INVOKE),
        @JsonSubTypes.Type(value = EventShutdown.class, name = ExtensionEvent.Type.Constants.SHUTDOWN)
})
@NoArgsConstructor
@RegisterForReflection
@ToString
@Getter
public abstract class ExtensionEvent {
    private Type eventType;
    private Long deadlineMs;

    @AllArgsConstructor
    @RegisterForReflection
    public static enum Type {
        @JsonProperty(Constants.SHUTDOWN)
        SHUTDOWN(Constants.SHUTDOWN),

        @JsonProperty(Constants.INVOKE)
        INVOKE(Constants.INVOKE);

        @JsonValue
        @Getter
        private final String value;

        public static class Constants {
            static final String SHUTDOWN = "SHUTDOWN";
            static final String INVOKE = "INVOKE";
        }
    }
}
