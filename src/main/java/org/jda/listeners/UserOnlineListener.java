package org.jda.listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static org.jda.JdaMain.*;
import static org.main.Variables.lukasID;
import static org.values.strings.Messages.LukasIsHere;

public class UserOnlineListener extends ListenerAdapter {

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        if (event.getUser().getId().equals(lukasID)) {
            if (event.getNewOnlineStatus() == OnlineStatus.ONLINE) {
                setCustomActivity(LukasIsHere);
                //Main.jda.getUserById(id).openPrivateChannel().flatMap(channel -> channel.sendMessage("HALLO SCHÖNEN TAG DU HUANSOHN\nᵘⁿᵈ ᵛᵉʳᵍᵉˢˢ ⁿᶦᶜʰᵗ ᵈᶦᵉ ʰᵃᵘˢᵃᵘᶠᵍᵃᵇᵉⁿ ᶻᵘ ᵃᵈᵈᵉⁿ")).queue();
            } else {
                setDefaultActivity();
            }
        }
    }
}
