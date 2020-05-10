package it.polimi.ingsw.client.ui.gui.utils;

public enum GodAsset {
    APOLLO("/assets/god_cards/apollo_card.png", "/assets/god_icons/apollo_icon.png", "apollo"),
    ARTEMIS("/assets/god_cards/artemis_card.png", "/assets/god_icons/artemis_icon.png", "artemis"),
    ATHENA("/assets/god_cards/athena_card.png", "/assets/god_icons/athena_icon.png", "athena"),
    ATLAS("/assets/god_cards/atlas_card.png", "/assets/god_icons/atlas_icon.png", "atlas"),
    DEMETER("/assets/god_cards/demeter_card.png", "/assets/god_icons/demeter_icon.png", "demeter"),
    HEPHAESTUS("/assets/god_cards/hephaestus_card.png", "/assets/god_icons/hephaestus_icon.png", "hephaestus"),
    HERA("/assets/god_cards/hera_card.png", "/assets/god_icons/hera_icon.png", "hera"),
    HESTIA("/assets/god_cards/hestia_card.png", "/assets/god_icons/hestia_icon.png", "hestia"),
    MINOTAUR("/assets/god_cards/minotaur_card.png", "/assets/god_icons/minotaur_icon.png", "minotaur"),
    PAN("/assets/god_cards/pan_card.png", "/assets/god_icons/pan_icon.png", "pan"),
    PROMETHEUS("/assets/god_cards/prometheus_card.png", "/assets/god_icons/prometheus_icon.png", "prometheus");

    public final String cardLocation;
    public final String iconLocation;
    public final String name; //English name all lowercase. If there are any spaces, substitute with an underscore.

    GodAsset(String cardLocation, String iconLocation, String name){
        this.cardLocation = cardLocation;
        this.iconLocation = iconLocation;
        this.name = name;
    }
}
