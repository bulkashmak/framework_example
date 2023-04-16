package org.example.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiTest {

    RequestSpecification reqresSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api")
            .addFilter(new ResponseLoggingFilter())
            .addFilter(new RequestLoggingFilter())
            .build();

    @Test
    @DisplayName("Получить всех пользователей со страницы")
    public void getUsersPageTest() {
        given()
                .when()
                .spec(reqresSpec)
                .get("/users?page=1")

                .then()
                .statusCode(200)
                .body("page", Matchers.is(1));
    }

    @Test
    @DisplayName("Получить пользователя по id")
    public void getUserByIdTest() {
        given()
                .when()
                .spec(reqresSpec)
                .get("/users/1")

                .then()
                .statusCode(200)
                .body("data.id", Matchers.is(1));
    }

    @Test
    @DisplayName("Создать пользователя")
    public void createUserTest() {
        RequestCreateUser request = new RequestCreateUser("Hamid", "qa");

        ResponseCreateUser response = given()
                .when()
                .spec(reqresSpec)
                .body(request)
                .post("/users")

                .then()
                .statusCode(201)
                .extract().as(ResponseCreateUser.class);

        Assertions.assertEquals(request.getName(), response.getName(),
                "Name в ответе не совпадает с Name в запросе");
        Assertions.assertEquals(request.getJob(), response.getJob(),
                "Job в ответе не совпадает с Job в запросе");
    }
}
