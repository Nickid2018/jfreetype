package io.github.mmc1234.jfreetype.example;

import jdk.incubator.foreign.ResourceScope;

public class DefaultScope {
    private ResourceScope scope;

    public ResourceScope get() {
        return scope;
    }

    public void setUp() {
        scope = ResourceScope.newConfinedScope();
    }

    public void tearDown() {
        scope.close();
        scope = null;
    }
}
