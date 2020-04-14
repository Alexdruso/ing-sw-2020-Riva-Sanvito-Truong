package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.utils.networking.Transmittable;

public class ClientDisconnectMessage extends ClientMessage implements DisconnectMessage {
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.DISCONNECT;
    }
}
