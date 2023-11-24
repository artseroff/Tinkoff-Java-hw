package edu.hw6.task6;

import org.jetbrains.annotations.NotNull;

public record PortInfo(Protocol protocol, int port, @NotNull String info) {

    private static final int PROTOCOL_WIDTH = 5;

    private static final int PORT_WIDTH = 9;

    public enum Protocol {
        UDP,
        TCP
    }

    @Override
    public String toString() {
        return ("%-" + PROTOCOL_WIDTH + "s").formatted(protocol.name()) + ("%-" + PORT_WIDTH + "s").formatted(port)
            + info;
    }

}


