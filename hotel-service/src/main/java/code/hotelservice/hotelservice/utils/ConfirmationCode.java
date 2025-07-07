package code.hotelservice.hotelservice.utils;

import java.util.UUID;

public class ConfirmationCode {
    public static String confirmationCode(){
        return UUID.randomUUID().toString();
    }
}
