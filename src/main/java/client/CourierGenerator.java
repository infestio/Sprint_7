package client;

import java.util.Random;


public class CourierGenerator {

    private static final String[] LOGINS = {"Рокки", "Арни", "ЖанКлод", "Джеки", "Несокрушимый"};
    private static final String[] PASSWORDS = {"1234", "4321", "бальбоа", "Чан"};
    private static final String[] FIRST_NAMES = {"Слай", "Шварц", "ВанДам", "Китаец"};

    public static Courier getRandom() {
        Courier courier = new Courier();
        courier.setLogin(LOGINS[new Random().nextInt(LOGINS.length)]);
        courier.setPassword(PASSWORDS[new Random().nextInt(PASSWORDS.length)]);
        courier.setFirstName(FIRST_NAMES[new Random().nextInt(FIRST_NAMES.length)]);
        return courier;
    }


}
