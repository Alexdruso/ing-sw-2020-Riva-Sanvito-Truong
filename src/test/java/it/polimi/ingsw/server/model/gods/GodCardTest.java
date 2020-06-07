package it.polimi.ingsw.server.model.gods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                "Hera",
                "Hestia",
                "Hypnus",
                "Minotaur",
                "Pan",
                "Prometheus",
                "Triton",
                "Zeus"
        };
        for (String godName : godNames) {
            God god = GodCard.valueOf(godName.toUpperCase()).getGod();
            assertEquals(god.getName(), godName);
        }
        assertEquals(godNames.length, GodCard.values().length);
    }
}
