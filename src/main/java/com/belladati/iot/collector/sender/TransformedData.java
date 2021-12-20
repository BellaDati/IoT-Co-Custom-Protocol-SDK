package com.belladati.iot.collector.sender;

import io.vertx.core.json.JsonArray;

public class TransformedData {

    /**
     * Max processed ID can be determined from the data, but is some cases the transfromation may need to keep some data
     * for next run and then it needs to expose the real max ID otherwise the records would be processed again.
     * Let's have an example - transformation processes 5 rows into 1 and then there come 8 rows, so transformation
     * would return 1 row with ID 5 (max of the processed data) and keep remaining 3 records for next run. It needs to
     * set maxProcessedId to 8 though, otherwise records 6-8 would be processed again. In next run when another 8 rows
     * come, it returns 2 rows for 6-10 and 11-15 and reports maxProcessedId 16.
     */
    private Long maxProcessedId;

    /**
     * Array of JsonObject processed data.
     * Each object needs to contain fields
     * ID with ID of the processed row, in case multiple rows are merged into one, then it's the last ID, the highest
     * JSON transformed data in the form of an JSON object
     */
    private JsonArray data;

    public TransformedData(Long maxProcessedId, JsonArray data) {
        this.maxProcessedId = maxProcessedId;
        this.data = data;
    }

    public TransformedData() {
    }

    public Long getMaxProcessedId() {
        return maxProcessedId;
    }

    public void setMaxProcessedId(Long maxProcessedId) {
        this.maxProcessedId = maxProcessedId;
    }

    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
    }
}
