package com.friday.challenge;

import java.security.InvalidParameterException;

public class AddressParser {

    private static final String HOUSE_PREFIX = " No ";
    private static final String STREET_IS_MISSING = "Street is missing";
    private static final String HOUSE_NUMBER_IS_MISSING = "House number is missing";
    private static final String INVALID_FORMAT_OF_ADDRESS_STRING = "Invalid format of address string";
    private static final String ADDRESS_STRING_CANNOT_BE_NULL_OR_EMPTY = "Address string cannot be null or empty";

    public static Address parseAddress(String addressToParse) {
        if (addressToParse == null || addressToParse.isEmpty())
            throw new InvalidParameterException(ADDRESS_STRING_CANNOT_BE_NULL_OR_EMPTY);

        if (addressToParse.contains(HOUSE_PREFIX)) return parseAddressWithHousePrefix(addressToParse);
        if (addressToParse.contains(",")) return parseAddressWithCommaSeparator(addressToParse);
        return parseAddressWithSpaceSeparator(addressToParse);
    }

    private static Address parseAddressWithHousePrefix(String addressToParse) {
        String[] parts = addressToParse.split(HOUSE_PREFIX);
        if (isHouseNumber(parts[1])) return createAddress(parts[0], HOUSE_PREFIX.trim() + " " + parts[1]);
        throw new IllegalArgumentException(HOUSE_NUMBER_IS_MISSING);
    }

    private static Address parseAddressWithCommaSeparator(String addressToParse) {
        String[] parts = addressToParse.split(",");
        if (isHouseNumber(parts[1])) return createAddress(parts[0], parts[1]);
        if (isHouseNumber(parts[0])) return createAddress(parts[1], parts[0]);
        throw new IllegalArgumentException(HOUSE_NUMBER_IS_MISSING);
    }

    private static Address parseAddressWithSpaceSeparator(String addressToParse) {
        String[] parts = addressToParse.trim().split(" ");
        if (parts.length == 1) throw new IllegalArgumentException(INVALID_FORMAT_OF_ADDRESS_STRING);
        if (isHouseNumber(parts[0])) return createAddress(addressToParse.substring(parts[0].length()), parts[0]);

        String houseNumber = findHouseNumber(addressToParse);
        String[] clearParts = addressToParse.split(houseNumber);
        return createAddress(clearParts[0], addressToParse.substring(addressToParse.indexOf(houseNumber)));
    }

    private static String findHouseNumber(String string) {
        String[] parts = string.trim().split(" ");
        String houseNumber = null;
        for (int i = 0; i < parts.length; i++) {
            if (isHouseNumber(parts[i])) {
                houseNumber = parts[i];
                break;
            }
        }
        if (houseNumber == null) throw new IllegalArgumentException(HOUSE_NUMBER_IS_MISSING);
        return houseNumber;
    }

    private static boolean isHouseNumber(String string) {
        return Character.isDigit(string.trim().charAt(0));
    }

    private static Address createAddress(String street, String houseNumber) {
        if (street.trim().isEmpty()) throw new IllegalArgumentException(STREET_IS_MISSING);
        if (houseNumber.trim().isEmpty()) throw new IllegalArgumentException(HOUSE_NUMBER_IS_MISSING);
        return new Address(street.trim(), houseNumber.trim());
    }

}
