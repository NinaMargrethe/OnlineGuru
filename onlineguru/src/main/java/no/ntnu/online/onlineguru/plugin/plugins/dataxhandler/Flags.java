package no.ntnu.online.onlineguru.plugin.plugins.dataxhandler;

/**
 * User: Nina Margrethe Smørsgård
 * GitHub: https://github.com/NinaMargrethe/
 * Date: 4/22/13
 */
public enum Flags {
    ON("on"),
    OFF("off"),
    QUEUE("queue");

    String action;

    Flags(String a) {
        action = a;
    }
}
