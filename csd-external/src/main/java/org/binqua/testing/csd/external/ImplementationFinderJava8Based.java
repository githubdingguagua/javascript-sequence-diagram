package org.binqua.testing.csd.external;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ImplementationFinderJava8Based<T> implements ImplementationFinder<T>{

    @Override
    public T findAnImplementationOf(Class<T> aClass, Object[] implementations) {

        if (thereAreNot(implementations)) {
            throw new RuntimeException(format("Not implementation of %s found because there are not implementations", aClass.getName()));
        }

        Optional<Object> objectOptional = Arrays.stream(implementations).filter(x -> aClass.isInstance(x)).findFirst();

        if (!objectOptional.isPresent()) {
            throw new RuntimeException(format("Not implementation of %s found in:\n%s", aClass.getName(), toMessage(implementations)));
        }


        return (T) objectOptional.get();

    }

    private boolean thereAreNot(Object[] implementations) {
        return implementations == null || implementations.length == 0;
    }

    private String toMessage(Object[] implementations) {
        return Arrays.stream(implementations).map(x -> x.getClass().getCanonicalName()).collect(Collectors.joining("\n"));
    }

}
