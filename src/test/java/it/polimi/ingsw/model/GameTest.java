package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void cannotRegisterMoreUsersThanDeclared(){
        Game game = new Game(3);
        game.subscribeUser(new User("Romano Prodi"));
        game.subscribeUser(new User("Sghiribizou"));
        game.subscribeUser(new User("Nick Name"));
        assertThrows(IllegalStateException.class,
                ()-> game.subscribeUser(new User("Luccio")));
        Game game2 = new Game(2);
        game2.subscribeUser(new User("Nagito Komaeda"));
        game2.subscribeUser(new User("Francesco Totti"));
        assertThrows(IllegalStateException.class,
                () -> game.subscribeUser(new User("Genoveffo Brombeis")));
    }

    @Test
    public void testRegisterPlayers(){
        Game game = new Game(3);
        game.subscribeUser(new User("Maria Ortigli"));
        game.subscribeUser(new User("Buggo"));
        game.subscribeUser(new User("Zeb89"));
        List<Player> playerList = game.getPlayersList();
        assertEquals(playerList.get(0).getNickname(), "Maria Ortigli");
        assertEquals(playerList.get(1).getNickname(), "Buggo");
        assertEquals(playerList.get(2).getNickname(), "Zeb89");
    }
}
