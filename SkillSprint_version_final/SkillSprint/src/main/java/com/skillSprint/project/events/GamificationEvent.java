package com.skillSprint.project.events;

import org.springframework.context.ApplicationEvent;
import com.skillSprint.project.models.User;

public class GamificationEvent extends ApplicationEvent {
    private final User user;
    private final int xpAwarded;
    private final String reason; // Example: "Completed Hard Challenge"

    public GamificationEvent(Object source, User user, int xpAwarded, String reason) {
        super(source);
        this.user = user;
        this.xpAwarded = xpAwarded;
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public int getXpAwarded() {
        return xpAwarded;
    }

    public String getReason() {
        return reason;
    }
}
