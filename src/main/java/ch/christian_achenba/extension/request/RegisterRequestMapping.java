package ch.christian_achenba.extension.request;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

import java.util.List;

@StaticInitSafe
@ConfigMapping(prefix = "registerRequest")
interface RegisterRequestMapping {
    List<String> events();
}
