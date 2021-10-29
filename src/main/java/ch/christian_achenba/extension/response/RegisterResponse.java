package ch.christian_achenba.extension.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@RegisterForReflection
@Builder
public class RegisterResponse {
    @JsonProperty
    private String functionName;
    @JsonProperty
    private String functionVersion;
    @JsonProperty
    private String handler;
}
