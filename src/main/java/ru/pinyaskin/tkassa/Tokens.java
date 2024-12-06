package ru.pinyaskin.tkassa;

/**
 * Wrapper for easily working with token generator and validator.
 * <br />
 * Generate token:
 * <pre>
 *     {@code
 *     String token = Tokens.builder()
 *                          .setRequestBody(jsonRequest)
 *                          .setPassword(password)
 *                          .build();
 *     }
 * </pre>
 *
 * When you're generating token, just put json of your raw request at {@code .setRequestBody}
 * without password (put it in Tokens with {@code .setPassword})
 * <br />
 * Validate token:
 * <pre>
 *     {@code
 *     boolean isValid = Tokens.builder()
 *                             .setRequestBody(jsonRequest)
 *                             .setPassword(password)
 *                             .isValid();
 *     }
 * </pre>
 *
 * Put in {@code .setRequestBody()} raw request from TKassa (Tinkoff Kassa) with token.
 * Method {@code .isValid()} will return {@code true}, if token in request is valid.
 *
 * @author pinyaskin iliya
 */
public final class Tokens {
    public static TokenBuilder builder() {
        return new DefaultTokenBuilder();
    }

    public static boolean isTokenValid(String json, String password) {
         return Tokens.builder()
                .setRequestBody(json)
                .setPassword(password)
                .isValid();
    }
}
