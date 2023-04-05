package tests.customer;

import com.ubereats.ubereatsclone.authentication.classes.LoginCredentials;
import com.ubereats.ubereatsclone.customer.dto.CustomerDto;
import com.ubereats.ubereatsclone.customer.entity.CustomerAddress;
import com.ubereats.ubereatsclone.customer.entity.CustomerCart;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.nio.charset.Charset;
import java.util.Random;


@Test
public class CustomerCreation {


    String fullname;
    String password;
    String username;
    String jwtToken;
    @Test
    public void createCustomer() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        CustomerDto customerDto = new CustomerDto();
        this.fullname = new String(array, Charset.forName("UTF-8"));
        this.username = fullname + "@gmail.com";
        this.password = fullname + "!@#";
        String contactNumber = String.valueOf(new Random().nextLong());
        String favCuisine = "Italian";
        CustomerCart customerCart = new CustomerCart();
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setStreetAddress("XYZ");
        customerAddress.setCity("NYC");
        customerAddress.setPincode("08540");

        customerDto.setFullName(fullname);
        customerDto.setUsername(username);
        customerDto.setPassword(password);
        customerDto.setContactNumber(contactNumber);
        customerDto.setFavCuisine(favCuisine);
        customerDto.setCustomerCart(customerCart);
        customerDto.setCustomerAddress(customerAddress);

        RestAssured.baseURI = "http://localhost:1234/api/customers";

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(customerDto)
        .when()
        .post("/register")
        .then()
        .statusCode(201);
    }

    @Test(dependsOnMethods = "createCustomer")
    public void login() {
        LoginCredentials login = new LoginCredentials();
        login.setUsername(this.username);
        login.setPassword(this.password);

        RestAssured.baseURI = "http://localhost:1234/api/customers";

        Response response = RestAssured.get("/authenticate");
        this.jwtToken = response.getBody().toString();

//        System.out.println("----------------" + this.jwtToken + "----------------");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/authenticate")
                .then()
                .statusCode(200);

    }
}
