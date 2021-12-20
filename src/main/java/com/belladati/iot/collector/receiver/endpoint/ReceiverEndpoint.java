package com.belladati.iot.collector.receiver.endpoint;

import com.belladati.iot.collector.common.Field;
import com.belladati.iot.collector.common.FieldType;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.Map;

/**
 * Implement to provide processing of a custom protocol.
 */
public interface ReceiverEndpoint {

	/**
	 * Init this endpoint.
	 * @param prefix on a receiver that invoked this endpoint, ideal to use in logging
	 * @param vertx instance of VertX
	 * @param config endpoint JSON configuration
	 * @param endpointMessageProcessor message processor to invoke when a message is received
	 */
	void init(String prefix, Vertx vertx, JsonObject config, EndpointMessageProcessor endpointMessageProcessor);

	/**
	 * Start processing, e.g. setup periodic timer that will poll the endpoint
	 * @param aVoid just void, has to be here so "start" can be used in future composure
	 * @return future that will be completed once the endpoint starts
	 */
	Future<Void> start(Void aVoid);

	/**
	 * Stop processing
	 *
	 * @param aVoid just void, has to be here so "stop" can be used in future composure
	 * @return future that will be completed once the endpoint stops
	 */
	Future<Void> stop(Void aVoid);

	/**
	 * Tells whether this endpoint is running and consuming messages and passing them to processing.
	 * @return true when endpoint is running
	 */
	boolean isRunning();

	/**
	 * When endpoint is reloaded, new endpoint is initialized, so the old one needs to be killed. This should most likely
	 * do the same as stop.
	 */
	void killEndpoint();

	/**
	 * Optional name of this receiver, will replace label "CUSTOM" in endpoint selection in the IOT Console.
	 *
	 * @return name
	 */
	default String receiverName() { return null; }

	/**
	 * Fields to show in UI for this endpoint to fill in. Values will be passed in form of a JSON to the action. In case
	 * no fields are provided, textarea to input arbitrary JSON will be provided.
	 *
	 * @return fields and their label with type
	 */
	default Map<String, Field> configurationFields() {
		return Collections.singletonMap("jsonConfig", new Field("JSON Configuration", FieldType.LONGTEXT));
	}
}
