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
        game.subscribeUser(new User("Romano Prodi"), GodCard.ATHENA.getGod());
        game.subscribeUser(new User("Sghiribizou"), GodCard.APOLLO.getGod());
        game.subscribeUser(new User("Nick Name"), GodCard.ARTEMIS.getGod());
        assertThrows(IllegalStateException.class,
                ()-> game.subscribeUser(new User("Luccio"), GodCard.DEMETER.getGod()));
        Game game2 = new Game(2);
        game2.subscribeUser(new User("Nagito Komaeda"), GodCard.ARTEMIS.getGod());
        game2.subscribeUser(new User("Francesco Totti"), GodCard.APOLLO.getGod());
        assertThrows(IllegalStateException.class,
                () -> game.subscribeUser(new User("Genoveffo Brombeis"), GodCard.HEPHAESTUS.getGod()));
    }

    @Test
    public void testRegisterPlayers(){
        Game game = new Game(3);
        game.subscribeUser(new User("Maria Ortigli"), GodCard.APOLLO.getGod());
        game.subscribeUser(new User("Buggo"), GodCard.ARTEMIS.getGod());
        game.subscribeUser(new User("Zeb89"), GodCard.DEMETER.getGod());
        List<Player> playerList = game.getPlayersList();
        assertEquals(playerList.get(0).getNickname(), "Maria Ortigli");
        assertSame(playerList.get(0).getGod(), GodCard.APOLLO.getGod());
        assertEquals(playerList.get(1).getNickname(), "Buggo");
        assertSame(playerList.get(1).getGod(), GodCard.ARTEMIS.getGod());
        assertEquals(playerList.get(2).getNickname(), "Zeb89");
        assertSame(playerList.get(2).getGod(), GodCard.DEMETER.getGod());
    }
}
