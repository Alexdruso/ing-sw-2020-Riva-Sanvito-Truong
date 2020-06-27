package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

/**
 * This enum contains all assets needed for the various gods in the game
 */
public enum GodAsset {
    /**
     * Asset for Apollo
     */
    APOLLO("/assets/god_cards/apollo_card.png", "/assets/god_icons/apollo_icon.png", "apollo"),
    /**
     * Asset for Artemis
     */
    ARTEMIS("/assets/god_cards/artemis_card.png", "/assets/god_icons/artemis_icon.png", "artemis"),
    /**
     * Asset for Athena
     */
    ATHENA("/assets/god_cards/athena_card.png", "/assets/god_icons/athena_icon.png", "athena"),
    /**
     * Asset for Atlas
     */
    ATLAS("/assets/god_cards/atlas_card.png", "/assets/god_icons/atlas_icon.png", "atlas"),
    /**
     * Asset for Demeter
     */
    DEMETER("/assets/god_cards/demeter_card.png", "/assets/god_icons/demeter_icon.png", "demeter"),
    /**
     * Asset for Hephaestus
     */
    HEPHAESTUS("/assets/god_cards/hephaestus_card.png", "/assets/god_icons/hephaestus_icon.png", "hephaestus"),
    /**
     * Asset for Hera
     */
    HERA("/assets/god_cards/hera_card.png", "/assets/god_icons/hera_icon.png", "hera"),
    /**
     * Asset for Hestia
     */
    HESTIA("/assets/god_cards/hestia_card.png", "/assets/god_icons/hestia_icon.png", "hestia"),
    /**
     * Asset for Hypnus
     */
    HYPNUS("/assets/god_cards/hypnus_card.png", "/assets/god_icons/hypnus_icon.png", "hypnus"),
    /**
     * Asset for Minotaur
     */
    MINOTAUR("/assets/god_cards/minotaur_card.png", "/assets/god_icons/minotaur_icon.png", "minotaur"),
    /**
     * Asset for Pan
     */
    PAN("/assets/god_cards/pan_card.png", "/assets/god_icons/pan_icon.png", "pan"),
    /**
     * Asset for Prometheus
     */
    PROMETHEUS("/assets/god_cards/prometheus_card.png", "/assets/god_icons/prometheus_icon.png", "prometheus"),
    /**
     * Asset for Triton
     */
    TRITON("/assets/god_cards/triton_card.png", "/assets/god_icons/triton_icon.png", "triton"),
    /**
     * Asset for Zeus
     */
    ZEUS("/assets/god_cards/zeus_card.png", "/assets/god_icons/zeus_icon.png", "zeus");

    /**
     * Location of the card asset in the resources folder
     */
    public final String cardLocation;
    /**
     * Location of the icon asset in the resources folder
     */
    public final String iconLocation;
    /**
     * English name of the god all lowercase.
     * If there are any spaces, they should be substituted with an underscore.
     */
    public final String godName;

    /**
     * Enum constructor
     * @param cardLocation the location of the card in the resources folder
     * @param iconLocation the location of the icon in the resources folder
     * @param godName the name of the god in lowercase and with spaces substituted by underscores
     */
    GodAsset(String cardLocation, String iconLocation, String godName){
        this.cardLocation = cardLocation;
        this.iconLocation = iconLocation;
        this.godName = godName;
    }

    /**
     * This method returns the GodAsset corresponding to the given god
     * @param god the god of which we want to retrieve the asset
     * @return the GodAsset corresponding to the god
     */
    public static GodAsset fromReducedGod(ReducedGod god){
        return GodAsset.valueOf(god.getName().toUpperCase());
    }
}
