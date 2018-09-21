package org.binqua.testing.csd.httpclient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Method;

public class ExecutionContext {
    private static ThreadLocal<ExecutionContext> executionHolder = new ThreadLocal<>();

    public static ExecutionContext currentExecutionContext () {
        return executionHolder.get();
    }

    public static void setExecutionContext (ExecutionContext context) {
        executionHolder.set(context);
    }

    private final Method execution;
    private final Object[] arguments;

    public ExecutionContext(Method execution, Object[] arguments) {
        this.execution = execution;
        this.arguments = arguments;
    }

    public Method getExecution() {
        return execution;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
