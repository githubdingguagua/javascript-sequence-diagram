package org.binqua.testing.csd.httpclient;

import org.binqua.testing.csd.external.SystemAlias;

import java.util.Optional;

import static java.lang.String.format;

class JavaMethodNameDescriptionResolver implements DescriptionResolver {

    private String type;

    private JavaMethodNameDescriptionResolver(String type) {
        this.type = type;
    }

    @Override
    public String resolve(ExecutionContext executionContext, HttpMessage.HttpMethod httpMethod, SystemAlias from, HttpUri to) {
        String description = Optional.ofNullable(executionContext)
            .map(x -> x.getExecution().getName())
            .orElseGet(() -> "")
            .replaceAll(
                camelCaseFormat(),
                " "
            );

        return format("%s %s %s %s from %s to %s", to.value(), description, httpMethod, type, from.name(), to.alias().name());
    }

    @Override
    public String resolve(ExecutionContext executionContext, SystemAlias from, HttpUri to) {
        return format("%s %s from %s to %s", to.value(), type, from.name(), to.alias().name());
    }

    private String camelCaseFormat() {
        return format("%s|%s|%s",
                      "(?<=[A-Z])(?=[A-Z][a-z])",
                      "(?<=[^A-Z])(?=[A-Z])",
                      "(?<=[A-Za-z])(?=[^A-Za-z])"
        );
    }

    public static DescriptionResolver request() {
        return new JavaMethodNameDescriptionResolver("request");
    }

    public static DescriptionResolver response() {
        return new JavaMethodNameDescriptionResolver("response");
    }

}
