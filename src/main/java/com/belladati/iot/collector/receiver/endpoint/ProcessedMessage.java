package com.belladati.iot.collector.receiver.endpoint;

import io.vertx.core.json.JsonObject;

public class ProcessedMessage {

    private boolean isIgnore;
    private boolean isError;
    private int errorHttpCode;
    private long timestamp;
    private String sensorId;
    private String error;
    private Exception exception;

    private JsonObject message, lastMessage;

    public ProcessedMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public ProcessedMessage(String sensorId, boolean isIgnore) {
        this();
        this.sensorId = sensorId;
        this.isIgnore = isIgnore;
    }

    public ProcessedMessage(String sensorId, JsonObject message, JsonObject lastMessage) {
        this();
        this.sensorId = sensorId;
        this.message = message;
        this.lastMessage = lastMessage;
    }

    public ProcessedMessage(String error, int errorHttpCode, JsonObject message) {
        this();
        this.isError = true;
        this.error = error;
        this.errorHttpCode = errorHttpCode;
        this.message = message;
    }

    public ProcessedMessage(String error, int errorHttpCode, Exception exception, JsonObject message) {
        this(error, errorHttpCode, message);
        this.exception = exception;
    }

    public boolean isError() {
        return isError;
    }

    public String getError() {
        return error;
    }

    public int getErrorHttpCode() {
        return errorHttpCode;
    }

    public Exception getException() {
        return exception;
    }

    public JsonObject getMessage() {
        return message;
    }

    public JsonObject getLastMessage() {
        return lastMessage;
    }

    public String getSensorId() {
        return sensorId;
    }

    public boolean isIgnore() {
        return isIgnore;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
