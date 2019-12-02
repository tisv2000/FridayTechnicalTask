## Friday technical task
  
### The task  
https://gist.github.com/MMore/dc09c01f62a65f6886f440baa0e549c7

### Prerequisites
- JDK8 or higher
- Maven v3.5.0
- Git v2.14
- Lombok plugin for IDE

### How to run the tests
    cd <your_projects_directory>
    git clone https://github.com/tisv2000/FridayTechnicalTask.git FridayTechnicalTask
    cd FridayTechnicalTask
    mvn clean test

#### Options for splitting the street and house numbers:
1. Separator is a string " No ". The house number goes after it, while the separator itself is a part of the house number (without an initial space).
2. Separator is a comma. We assume that there can only be one (or zero) comma in a line. There are two options:
    - The house number goes after the comma.
    - The house number goes before the comma.
3. Separator is a space. There are two options:
    - The house number goes at the beginning of the line.
    - The house number goes at the end of the line. At the same time, a letter may follow it through a space. For example "23 b".
4. The house number is a string, which starts with a digit, but can also include letters. For example "123B".

#### Requirements for Address class:
1. It must have string fields: street and housenumber.
2. It must have constructor and getters for street and housenumber fields (Lombok can be used).
3. It must be serializable into JSON by using Jackson: method toJsonString().

#### Requirements for AddressParser class:
1. It must have static method parseAddress(), which parse address string and returns an Address instance.

#### Requirements for tests:
1. Negative tests on invalid values (null, "") for the address string: testInvalidStringOfAddress.
2. Test on serialization Address object into JSON, method toJsonString(): testAddressToJsonString.
3. Main test: testParseAddress.
  It depends on testAddressToJsonString, because if there are some errors in serialization, there is no sense to check all the rest.
4. Negative test on invalid address format (house number is skipped, street is not specified...): testParseInvalidAddress.

#### Personal notes:
I took the liberty of expanding the technical task a little bit, by adding additional cases, negative tests and service methods' tests.
There are some notes about what else was done:
1. TDD approach - tests go first, then functionality.
2. DDT approach - different sets of data for the same tests.
3. I decided not to extract testAddressToJsonString method into a separate class - AddressTest, and put it into AddressParserTest class.
4. JsonProcessingException is not handled, it's thrown from testing method.
