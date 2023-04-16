package org.example.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;

public class WebTests {

    public static final String BASE_URI = "https://www.google.com/"; // Адресс нашего сайта

    public static final String SEARCH_FIELD_X = "//textarea"; // Xpath поля поиска
    public static final String SEARCH_BUTTON_X = "//input[@name=\"btnK\"]"; // Xpath кнопки поиска

    // Метод, помеченый аннотацией @BeforeEach, выполняется перед каждым тестом
    @BeforeEach
    public void setUp() {
        Selenide.open(BASE_URI); // Тут мы открываем сайт google.com
        WebDriverRunner.getWebDriver().manage().window().maximize(); // Тут мы максимизируем окно браузера для удобства
    }

    // Метод, помеченый аннотацией @AfterEach, выполняется после каждого теста
    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver(); // Тут мы закрываем браузер
    }

    @Test
    @DisplayName("Смена языка")
    public void searchTest() {
        Selenide.$x(SEARCH_FIELD_X).sendKeys("Ingushetia"); // Набираем в поиск "Ингушетия"
        Selenide.$x(SEARCH_BUTTON_X).click(); // Нажимаем кнопку поиска

        Assertions.assertTrue(WebDriverRunner.driver().url().contains("Ingushetia")); // Проверяем, что поиск был задан корректно
    }
}
