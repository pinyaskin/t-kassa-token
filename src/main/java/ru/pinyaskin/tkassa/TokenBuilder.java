package ru.pinyaskin.tkassa;

public interface TokenBuilder {
    TokenBuilder setRequestBody(String json);
    TokenBuilder setPassword(String password);

    boolean isValid();
    String build();
}
