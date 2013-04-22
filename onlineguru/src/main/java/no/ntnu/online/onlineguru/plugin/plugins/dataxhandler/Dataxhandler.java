package no.ntnu.online.onlineguru.plugin.plugins.dataxhandler;

import no.fictive.irclib.event.container.Event;
import no.fictive.irclib.event.container.command.PrivMsgEvent;
import no.fictive.irclib.event.model.EventType;
import no.ntnu.online.onlineguru.plugin.control.EventDistributor;
import no.ntnu.online.onlineguru.plugin.model.Plugin;
import no.ntnu.online.onlineguru.utils.Wand;

import java.util.ArrayList;

/**
 * User: Nina Margrethe Smørsgård
 * GitHub: https://github.com/NinaMargrethe/
 * Date: 4/22/13
 */
public class Dataxhandler implements Plugin {

    private ArrayList<String> queue = new ArrayList<String>();
    private boolean isAvailable;
    private Wand wand;
    private final String DESCRIPTION = "Queuehandler for Bank- og Økonomikomiteen (banKom) in Mammut Datax";
    private final String DATAXTRIGGER = "!datax";
    private Flags flags;
    private String dataxholder = queue.get(0);
    /**
     * The nick of the person invoking the command !datax
     */
    private String initiator;

    public Dataxhandler() {

    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public void incomingEvent(Event e) {
        switch (e.getEventType()) {
            case PRIVMSG:
                PrivMsgEvent privMsgEvent = (PrivMsgEvent) e;
                String[] message = privMsgEvent.getMessage().split("\\s+");

                if (isMessageForPlugin(message)) {
                    //sendLmgtfyLink(privMsgEvent.getNetwork(), privMsgEvent.getTarget(), new ArrayList<String>(Arrays.asList(message).subList(1, message.length)));
                    queueAction(message);

                }
        }
    }

    private void queueAction(String[] message) {

        switch (flags) {
            case ON:
                if (!queue.isEmpty()) {
                    queue.add(initiator);
                    printMessage("Datax is occupid by " + getDataxholder() + ". You are now queued as number " + queue.size());
                } else {
                    queue.add(initiator);
                    printMessage("Datax is now occupied by " + getDataxholder());
                }
            case OFF:
                if (queue.size() > 1 && initiator == getDataxholder()) {
                    queue.remove(0);
                    printMessage("Datax is now ready for use by " + getDataxholder());
                    isAvailable(false);
                } else {
                    printMessage("Datax is now ready for use");
                    isAvailable(true);
                }


        }

    }

    private boolean isAvailable(boolean b) {
        isAvailable = b;
        return isAvailable;
    }

    private boolean isAvailable(ArrayList queue) {
        isAvailable = queue.isEmpty();
        return isAvailable;
    }

    private boolean isMessageForPlugin(String[] message) {
        return message != null && message.length > 1 && message[0].equalsIgnoreCase(DATAXTRIGGER);
    }

    public void addEventDistributor(EventDistributor eventDistributor) {
        eventDistributor.addListener(this, EventType.PRIVMSG);
    }

    public void addWand(Wand wand) {
        this.wand = wand;
    }

    public String getDataxholder() {
        if (!queue.isEmpty()) {
            dataxholder = queue.get(0);
        } else dataxholder = "none";

        return dataxholder;
    }
}
