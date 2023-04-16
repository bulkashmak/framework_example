package org.example.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ApiTest {

    // Это небольшая спецификация для библиотеки Rest Assured
    RequestSpecification reqresSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api") // Тут мы указываем нам адресс
            .addFilter(new ResponseLoggingFilter()) // Тут мы добавили фильтр, который будет в консоли отображать наши запросы
            .addFilter(new RequestLoggingFilter()) // Тут мы добавили фильтр, который будет в консоли отображать наши ответы
            .build(); // Тут мы собрали спецификацию

    @Test // Это аннотация библиотеки jupiter. Метод, который отмечен этой аннотацией, является тестом
    @DisplayName("Получить всех пользователей со страницы") // Эта аннотация просто дает нашему тесту какое-то название
    public void getUsersPageTest() {
        RestAssured.given() // В библиотеке RestAssured, чтобы создать запрос, нужно использовать метод given()
                .when() // When, дословно Когда. Это синтаксический сахар, но он делает код читабельнее. Тут мы указываем то, что будет в нашем запросе
                .spec(reqresSpec) // Тут мы пропихнули нашу спецификацию
                .get("/users?page=1") // Тут мы отправляем GET запрос

                .then() // Then - это тоже синтакцический сахар. Тут мы указываем, что должно быть в ответе
                .statusCode(200) // Проверка на ожидаемый статус код ответа
                .body("page", Matchers.is(1)); // Проверка ответа. Тут мы проверили, что страница в ответе совпадает с той, что мы запросили
    }

    @Test
    @DisplayName("Получить пользователя по id")
    public void getUserByIdTest() {
        RestAssured.given()
                .when()
                .spec(reqresSpec)
                .get("/users/1")

                .then()
                .statusCode(200)
                .body("data.id", Matchers.is(1));
    }

    // В этом тесте я решил показать, как работать с DTO
    @Test
    @DisplayName("Создать пользователя")
    public void createUserTest() {
        // Тут мы создали тело нашего запроса в виде объекта
        RequestCreateUser request = new RequestCreateUser("Hamid", "qa");

        ResponseCreateUser response = RestAssured.given()
                .when()
                .spec(reqresSpec)
                .body(request) // Тут мы закинули наш объект в запрос. Библиотека RestAssured сама преобразует наш объект в json тело запроса
                .post("/users")

                .then()
                .statusCode(201)
                .extract().as(ResponseCreateUser.class); // Тут мы преобразовали наш ответ в объект, чтобы далее с ним поработать

        Assertions.assertEquals(request.getName(), response.getName(),
                "Name в ответе не совпадает с Name в запросе"); // Проверка на то, что Name в запросе совпадает с Name в ответе
        Assertions.assertEquals(request.getJob(), response.getJob(),
                "Job в ответе не совпадает с Job в запросе"); // Проверка на то, что Job в запросе совпадает с Job в ответе
    }
}
