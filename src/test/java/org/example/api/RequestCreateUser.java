package org.example.api;

import java.util.Objects;

/**
 * Это DTO (Data Transfer Object) класс.
 * С его помощью можно преобразовать, например JSON, в объект.
 * Это нужно для удобства. С объктами проще работать.
 */
public class RequestCreateUser {

    private String name;
    private String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public RequestCreateUser(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCreateUser that = (RequestCreateUser) o;
        return Objects.equals(name, that.name) && Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, job);
    }
}
