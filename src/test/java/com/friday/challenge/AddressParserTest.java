package com.friday.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

public class AddressParserTest {

    @Test(dataProvider = "invalidStringOfAddressDataProvider", expectedExceptions = InvalidParameterException.class)
    public void testInvalidStringOfAddress(String stringOfAddress) {
        AddressParser.parseAddress(stringOfAddress);
    }

    @DataProvider
    public static Object[][] invalidStringOfAddressDataProvider() {
        return new Object[][]{
                {null},
                {""}
        };
    }

    @Test
    public void testAddressToJsonString() throws JsonProcessingException {
        Address address = new Address("Some street", "123");
        String actualJsonString = address.toJsonString();
        String expectedJsonString = "{\"street\":\"Some street\",\"housenumber\":\"123\"}";
        Assert.assertEquals(actualJsonString, expectedJsonString);
    }

    @Test(dataProvider = "parseAddressDataProvider", dependsOnMethods = "testAddressToJsonString")
    public void testParseAddress(String stringOfAddress, String expectedJson) throws JsonProcessingException {
        String actualJson = AddressParser.parseAddress(stringOfAddress).toJsonString();
        Assert.assertEquals(actualJson, expectedJson);
    }

    @DataProvider
    public static Object[][] parseAddressDataProvider() {
        return new Object[][]{
                // Simple cases
                {"Winterallee 3", "{\"street\":\"Winterallee\",\"housenumber\":\"3\"}"},
                {"Musterstrasse 45", "{\"street\":\"Musterstrasse\",\"housenumber\":\"45\"}"},
                {"Blaufeldweg 123B", "{\"street\":\"Blaufeldweg\",\"housenumber\":\"123B\"}"},
                // More complicated cases
                {"Am Bächle 23", "{\"street\":\"Am Bächle\",\"housenumber\":\"23\"}"},
                {"Auf der Vogelwiese 23 b", "{\"street\":\"Auf der Vogelwiese\",\"housenumber\":\"23 b\"}"},
                // Complex cases
                {"4, rue de la revolution", "{\"street\":\"rue de la revolution\",\"housenumber\":\"4\"}"},
                {"200 Broadway Av", "{\"street\":\"Broadway Av\",\"housenumber\":\"200\"}"},
                {"Calle Aduana, 29", "{\"street\":\"Calle Aduana\",\"housenumber\":\"29\"}"},
                {"Calle 39 No 1540", "{\"street\":\"Calle 39\",\"housenumber\":\"No 1540\"}"}
        };
    }

    @Test(dataProvider = "parseInvalidAddressDataProvider", expectedExceptions = IllegalArgumentException.class, dependsOnMethods = "testAddressToJsonString")
    public void testParseInvalidAddress(String stringOfAddress) throws JsonProcessingException {
        AddressParser.parseAddress(stringOfAddress).toJsonString();
    }

    @DataProvider
    public static Object[][] parseInvalidAddressDataProvider() {
        return new Object[][]{
                {"Winterallee"},
                {"  Musterstrasse "},
                {"23"},
                {"TEXT, rue de la revolution"},
                {"Calle Aduana, TEXT"},
                {"Calle 39 No TEXT"}
        };
    }
}