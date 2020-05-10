package it.polimi.ingsw.client.ui.gui.utils;

public enum GodAsset {
    APOLLO("/assets/god_cards/apollo_card.png", "/assets/god_icons/apollo_icon.png"),
    ARTEMIS("/assets/god_cards/artemis_card.png", "/assets/god_icons/artemis_icon.png"),
    ATHENA("/assets/god_cards/athena_card.png", "/assets/god_icons/athena_icon.png"),
    ATLAS("/assets/god_cards/atlas_card.png", "/assets/god_icons/atlas_icon.png"),
    DEMETER("/assets/god_cards/demeter_card.png", "/assets/god_icons/demeter_icon.png"),
    HEPHAESTUS("/assets/god_cards/hephaestus_card.png", "/assets/god_icons/hephaestus_icon.png"),
    HERA("/assets/god_cards/hera_card.png", "/assets/god_icons/hera_icon.png"),
    HESTIA("/assets/god_cards/hestia_card.png", "/assets/god_icons/hestia_icon.png"),
    MINOTAUR("/assets/god_cards/minotaur_card.png", "/assets/god_icons/minotaur_icon.png"),
    PAN("/assets/god_cards/pan_card.png", "/assets/god_icons/pan_icon.png"),
    PROMETHEUS("/assets/god_cards/prometheus_card.png", "/assets/god_icons/prometheus_icon.png");

    public final String cardLocation;
    public final String iconLocation;

    GodAsset(String cardLocation, String iconLocation){
        this.cardLocation = cardLocation;
        this.iconLocation = iconLocation;
    }
}
