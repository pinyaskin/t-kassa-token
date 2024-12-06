package ru.pinyaskin.tkassa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokensUT {
    @Test
    @DisplayName("Should return valid token")
    void buildToken_shouldReturnValidToken() {
        // Append
        String request = """
                {
                  "TerminalKey": "MerchantTerminalKey",
                  "Amount": 19200,
                  "OrderId": "21090",
                  "Description": "Подарочная карта на 1000 рублей",
                  "Token": "68711168852240a2f34b6a8b19d2cfbd296c7d2a6dff8b23eda6278985959346",
                  "DATA": {
                    "Phone": "+71234567890",
                    "Email": "a@test.com"
                  },
                  "Receipt": {
                    "Email": "a@test.ru",
                    "Phone": "+79031234567",
                    "Taxation": "osn",
                    "Items": [
                      {
                        "Name": "Наименование товара 1",
                        "Price": 10000,
                        "Quantity": 1,
                        "Amount": 10000,
                        "Tax": "vat10",
                        "Ean13": "303130323930303030630333435"
                      },
                      {
                        "Name": "Наименование товара 2",
                        "Price": 3500,
                        "Quantity": 2,
                        "Amount": 7000,
                        "Tax": "vat20"
                      },
                      {
                        "Name": "Наименование товара 3",
                        "Price": 550,
                        "Quantity": 4,
                        "Amount": 4200,
                        "Tax": "vat10"
                      }
                    ]
                  }
                }
                """;
        String expectedToken = "0024a00af7c350a3a67ca168ce06502aa72772456662e38696d48b56ee9c97d9";

        // Act
        String token = Tokens.builder()
                .setRequestBody(request)
                .setPassword("usaf8fw8fsw21g")
                .build();

        // Assert
        assertEquals(token, expectedToken);
    }

    @Test
    @DisplayName("isTokenValid should return true")
    void isTokenValid_shouldReturnTrue() {
        // Append
        String req = """
                {
                  "TerminalKey": "15180119216597",
                  "PaymentId": "5353155",
                  "Amount": "851500",
                  "Receipt": {
                    "Email": "ermilove78@mail.ru",
                    "Taxation": "osn",
                    "Items": [
                      {
                        "Name": "Футболка-поло с золотистым воротничком",
                        "Price": "728000",
                        "Quantity": "1",
                        "Amount": "364000",
                        "Tax": "vat18"
                      }
                    ]
                  },
                  "Token": "a2804ae88b7a71d6bf831ea497f8df0768864002459eaeef7a0c50835d72810a"
                }
                """;

        // Act
        boolean actual = Tokens.isTokenValid(req, "asd21dsas2");

        // Assert
        assertTrue(actual);
    }
}
