package org.bk.sample.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageAcknowledgement {

    private final String id;
    private final String received;
    private final String payload;

    @JsonCreator
    public MessageAcknowledgement(@JsonProperty("id") String id,
                                  @JsonProperty("received") String received,
                                  @JsonProperty("payload") String payload) {
        this.id = id;
        this.received = received;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public String getReceived() {
        return received;
    }

}
