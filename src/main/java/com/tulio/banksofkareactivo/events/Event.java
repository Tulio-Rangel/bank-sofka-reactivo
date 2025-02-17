package com.tulio.banksofkareactivo.events;

import java.time.Instant;
import java.util.UUID;

public abstract class Event {

    private String id;

    public Event() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

}
