package org.binqua.testing.csd.client.mockito;

public class RequestArgument {

    private String toStringReturnValue;

    public RequestArgument(String toStringReturnValue) {
        this.toStringReturnValue = toStringReturnValue;
    }

    @Override
    public String toString() {
        return toStringReturnValue;
    }
}
