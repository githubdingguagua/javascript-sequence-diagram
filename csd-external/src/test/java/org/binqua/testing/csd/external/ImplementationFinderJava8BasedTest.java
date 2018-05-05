package org.binqua.testing.csd.external;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ImplementationFinderJava8BasedTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final ImplementationFinderJava8Based<MyClass> implementationFinderJava8Based = new ImplementationFinderJava8Based<>();

    @Test
    public void givenAnImplementationIsPresentThenFindAnImplementationOfFindsTheRightImplementation() throws Exception {

        Object actualImplementation = implementationFinderJava8Based.findAnImplementationOf(MyClass.class, new Object[]{"", new MyClass()});

        assertThat(actualImplementation, is(instanceOf(MyClass.class)));

    }

    @Test
    public void givenAnImplementationIsNotPresentThenFindAnImplementationThrowsRuntimeException() throws Exception {

        expectedException.expect(RuntimeException.class);

        expectedException.expectMessage("Not implementation of org.binqua.testing.csd.external.ImplementationFinderJava8BasedTest$MyClass found in:\njava.lang.String\njava.lang.Integer");

        implementationFinderJava8Based.findAnImplementationOf(MyClass.class, new Object[]{"", new Integer(1)});

    }

    @Test
    public void givenNoImplementationsThenFindAnImplementationThrowsRuntimeException() throws Exception {

        assertThatExceptionMessageIsCorrectInCaseOfImplementations(new Object[0]);

    }

    @Test
    public void givenNullImplementationsThenFindAnImplementationThrowsRuntimeException() throws Exception {

        assertThatExceptionMessageIsCorrectInCaseOfImplementations(null);

    }

    private void assertThatExceptionMessageIsCorrectInCaseOfImplementations(Object[] implementations) {
        expectedException.expect(RuntimeException.class);

        expectedException.expectMessage("Not implementation of org.binqua.testing.csd.external.ImplementationFinderJava8BasedTest$MyClass found because there are not implementations");

        implementationFinderJava8Based.findAnImplementationOf(MyClass.class, implementations);
    }

    interface MyInterface {
    }

    class MyClass implements MyInterface {

    }
}