package tests;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class FirstTest {

    @Test
    public void test1() {
        Response response = get("https://reqres.in/api/users?page=2");
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test(dependsOnMethods = "test1")
    public void test2() {
        baseURI = "https://reqres.in/api";
        given().get("/users?page=2").then().statusCode(200);
    }
}