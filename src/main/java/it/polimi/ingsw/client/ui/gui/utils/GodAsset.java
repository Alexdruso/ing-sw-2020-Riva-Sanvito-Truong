package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

/**
 * This enum contains all assets needed for the various gods in the game
 */
public enum GodAsset {
    APOLLO("/assets/god_cards/apollo_card.png", "/assets/god_icons/apollo_icon.png", "apollo"),
    ARTEMIS("/assets/god_cards/artemis_card.png", "/assets/god_icons/artemis_icon.png", "artemis"),
    ATHENA("/assets/god_cards/athena_card.png", "/assets/god_icons/athena_icon.png", "athena"),
    ATLAS("/assets/god_cards/atlas_card.png", "/assets/god_icons/atlas_icon.png", "atlas"),
    DEMETER("/assets/god_cards/demeter_card.png", "/assets/god_icons/demeter_icon.png", "demeter"),
    HEPHAESTUS("/assets/god_cards/hephaestus_card.png", "/assets/god_icons/hephaestus_icon.png", "hephaestus"),
    HERA("/assets/god_cards/hera_card.png", "/assets/god_icons/hera_icon.png", "hera"),
    HESTIA("/assets/god_cards/hestia_card.png", "/assets/god_icons/hestia_icon.png", "hestia"),
    HYPNUS("/assets/god_cards/hypnus_card.png", "/assets/god_icons/hypnus_icon.png", "hypnus"),
    MINOTAUR("/assets/god_cards/minotaur_card.png", "/assets/god_icons/minotaur_icon.png", "minotaur"),
    PAN("/assets/god_cards/pan_card.png", "/assets/god_icons/pan_icon.png", "pan"),
    PROMETHEUS("/assets/god_cards/prometheus_card.png", "/assets/god_icons/prometheus_icon.png", "prometheus"),
    TRITON("/assets/god_cards/triton_card.png", "/assets/god_icons/triton_icon.png", "triton"),
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
     * @param cardLocation
     * @param iconLocation
     * @param godName
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
