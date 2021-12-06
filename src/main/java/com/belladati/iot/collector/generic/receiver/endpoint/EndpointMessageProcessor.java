package com.belladati.iot.collector.generic.receiver.endpoint;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * Processor for messages. Its actuall implementation is the receiver itself and it will be injected into
 * the endpoint. No need to implement this interface in an extension.
 */
public interface EndpointMessageProcessor {

	/**
	 * Messages may not be accepted when receiver is paused, so before processing a message, check that it can be processed.
	 * @return true when it's OK to process message
	 */
	boolean onMessageStart();

	/**
	 * Processes streamed message
	 *
	 * @param messageBuffer
	 * @return
	 */
	default ProcessedMessage processMessage(Buffer messageBuffer) {
		return processMessage(getJson(messageBuffer));
	}

	/**
	 * Processes JSON message (or message converted to JSON)
	 *
	 * @param messageJson
	 * @return
	 */
	ProcessedMessage processMessage(JsonObject messageJson);

	/**
	 * Stores processed message into the database for further processing by sender.
	 * Invoke this method only when message is not ignored or is not error.
	 * @param processedMessage
	 */
	void finishProcessing(ProcessedMessage processedMessage);

	/**
	 * Converts stream data into JSON based on configured message content type
	 * @param b stream data
	 * @return parsed JSON
	 */
	JsonObject getJson(Buffer b);

}
