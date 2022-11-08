package com.belladati.iot.collector.sender;

import com.belladati.iot.collector.common.Field;
import com.belladati.iot.collector.common.FieldType;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.Map;

public interface DataTransformer {

    /**
     * Unique transformation ID
     * @return action ID
     */
    String transformationId();

    /**
     * Transformation name displayed in console
     * @return action name
     */
    String transformationName();

    /**
     * Init data transformation with provided configuration
     *
     * @param config configuration
     */
    void init(JsonObject config, Sender sender);

    /**
     * Transform incoming array of data into transformed data
     * @param data incoming data
     * @return outgoing data
     */
    TransformedData transform(JsonArray data);

    /**
     * Fields to show in UI for this transformation to fill in. Values will be passed in form of a JSON to the transformation.
     * In case no fields are provided, textarea to input arbitrary JSON will be provided.
     *
     * @return fields and their type
     */
    default Map<String, Field> configurationFields() {
        return Collections.singletonMap("jsonConfig", new Field("JSON Configuration", FieldType.LONGTEXT));
    }
}
