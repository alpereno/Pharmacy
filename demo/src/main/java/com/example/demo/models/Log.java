package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

public class Log {
    private String trIdNo;
    private String logMessage;
    private Timestamp timeStamp;

    public Log() {
    }

    public Log(@JsonProperty String trIdNo, @JsonProperty String logMessage, @JsonProperty Timestamp timeStamp) {
        this.trIdNo = trIdNo;
        this.logMessage = logMessage;
        this.timeStamp = timeStamp;
    }

    public String getTrIdNo() {
        return trIdNo;
    }

    public void setTrIdNo(String trIdNo) {
        this.trIdNo = trIdNo;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

}
