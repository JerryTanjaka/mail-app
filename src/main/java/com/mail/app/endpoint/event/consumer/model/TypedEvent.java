package com.mail.app.endpoint.event.consumer.model;

import com.mail.app.PojaGenerated;
import com.mail.app.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
