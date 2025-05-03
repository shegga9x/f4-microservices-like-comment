package com.f4.commentlike.handler;

public interface EventHandler<T> {
    String getEventName(); // e.g. "postReel"

    void handle(T payload);
}
