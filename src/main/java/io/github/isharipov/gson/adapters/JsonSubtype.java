package io.github.isharipov.gson.adapters;

public @interface JsonSubtype {
    Class<?> clazz();

    String name();
}
