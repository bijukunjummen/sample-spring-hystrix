package agg.samples.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String id;
    private String payload;

    @JsonProperty("throw_exception")
    private boolean throwException;

    @JsonProperty("delay_by")
    private int delayBy = 0;

    public Message() {
        //
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    public void setDelayBy(int delayBy) {
        this.delayBy = delayBy;
    }
}
