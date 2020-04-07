package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.GodCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void cannotRegisterMoreUsersThanDeclared(){
        Game game = new Game(3);
        game.subscribeUser("Romano Prodi", GodCard.ATHENA.getGod());
        game.subscribeUser("Sghiribizou", GodCard.APOLLO.getGod());
        game.subscribeUser("Nick Name", GodCard.ARTEMIS.getGod());
        assertThrows(IllegalStateException.class,
                ()-> game.subscribeUser("Luccio", GodCard.DEMETER.getGod()));
        Game game2 = new Game(2);
        game2.subscribeUser("Nagito Komaeda", GodCard.ARTEMIS.getGod());
        game2.subscribeUser("Francesco Totti", GodCard.APOLLO.getGod());
        assertThrows(IllegalStateException.class,
                () -> game.subscribeUser("Genoveffo Brombeis", GodCard.HEPHAESTUS.getGod()));
    }

    @Test
    public void testRegisterPlayers(){
        Game game = new Game(3);
        game.subscribeUser("Maria Ortigli", GodCard.APOLLO.getGod());
        game.subscribeUser("Buggo", GodCard.ARTEMIS.getGod());
        game.subscribeUser("Zeb89", GodCard.DEMETER.getGod());
        List<Player> playerList = game.getPlayersList();
        assertEquals(playerList.get(0).getNickname(), "Maria Ortigli");
        assertSame(playerList.get(0).getGod(), GodCard.APOLLO.getGod());
        assertEquals(playerList.get(1).getNickname(), "Buggo");
        assertSame(playerList.get(1).getGod(), GodCard.ARTEMIS.getGod());
        assertEquals(playerList.get(2).getNickname(), "Zeb89");
        assertSame(playerList.get(2).getGod(), GodCard.DEMETER.getGod());
    }
}