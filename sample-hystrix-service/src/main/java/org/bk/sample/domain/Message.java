package org.bk.sample.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private final String id;
    private final String payload;

    @JsonProperty("throw_exception")
    private final boolean throwException;

    @JsonProperty("delay_by")
    private final int delayBy;

    @JsonCreator
    public Message(@JsonProperty("id") String id,
                   @JsonProperty("payload") String payload,
                   @JsonProperty("throw_exception") boolean throwException,
                   @JsonProperty("delay_by") int delayBy) {
        this.id = id;
        this.payload = payload;
        this.throwException = throwException;
        this.delayBy = delayBy;
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isThrowException() {
        return throwException;
    }

    public int getDelayBy() {
        return delayBy;
    }

}
