package it.polimi.ingsw.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @ParameterizedTest
    //Classic names
    @ValueSource(strings = {"Pippo", "Pluto", "", "Marco Porcio Catone Uticense"})
    void rightName(String nickname) {
        User user = new User(nickname);
        assertEquals(user.nickname, nickname);
    }
}