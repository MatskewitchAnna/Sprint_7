package praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandomData {

    public static Courier randomCourier(){
        final String login = RandomStringUtils.randomAlphabetic(5);
        final String password = RandomStringUtils.randomNumeric(5);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
}
