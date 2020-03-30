package it.polimi.ingsw.model.gods;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GodCardTest {

    @Test
    public void godNames() {
        String[] godNames = new String[]{
                "Apollo",
                "Artemis",
                "Athena",
                "Atlas",
                "Demeter",
                "Hephaestus",
                "Minotaur",
                "Pan",
                "Prometheus",
        };
        for (String godName : godNames) {
            God god = GodCard.valueOf(godName.toUpperCase()).getGod();
            assertEquals(god.getName(), godName);
        }
    }
}
