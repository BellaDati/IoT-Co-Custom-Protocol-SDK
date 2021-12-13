package com.belladati.iot.collector.generic.sender.verticle.action;

import com.belladati.iot.collector.generic.sender.verticle.Sender;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface Action {

	/**
	 * Unique action ID
	 * @return action ID
	 */
	String actionId();

	/**
	 * Action name displayed in console
	 * @return action name
	 */
	String actionName();

	/**
	 * Configure action to be ready to be initialized
	 * @param sender instance of a sender that initializes this action
	 * @param config JSON configuration of the action
	 */
	void configure(Sender sender, JsonObject config);

	/**
	 * Initialize action, e.g. setup workers, connect to a database etc.
	 * @return completable future
	 */
	Future<Void> init();

	/**
	 * Process incoming data
	 * @param row data row
	 * @return completable future
	 */
	default Future<Void> process(JsonObject row) {
		return Future.succeededFuture();
	}

	/**
	 * Called when processing fails. Action should clean up it's state so it can e.g. reconnect.
	 * System will pause after a few failures and then try to reinitialize action.
	 */
	default void failedProcessing() {}

	/**
	 * Tell system whether this action is initialized and ready to process data.
	 * @return true when action is ready
	 */
	boolean isInitialized();

	/**
	 * Called before sender is reloaded, actions need to e.g. disconnect so they can be reinitialized.
	 * @return
	 */
	Future<Void> close();

	/**
	 * Handle result of an action. Just an utility method, does not need to be implemented or used.
	 *
	 * Typical usage is
	 * someAction(ar -> handleResult(true, ar, promise, (s) -> promise.complete()))
	 *
	 * @param continueOnError when result fails, determine whether to fail or just continue with normal operation
	 * @param result an action result
	 * @param globalHandler handler to call when result fails, either complete or fail
	 * @param successHandler handler to call when result is OK
	 * @param <T>
	 */
	default <T> void handleResult(boolean continueOnError, AsyncResult<T> result, Promise<Void> globalHandler, Handler<T> successHandler) {
		if (result.failed()) {
			if (continueOnError) {
				globalHandler.complete();
			} else {
				globalHandler.fail(result.cause());
			}
			failedProcessing();
		} else {
			successHandler.handle(result.result());
		}
	}

	/**
	 * Splits bulk messages into rows and calls "process" on each row. Can be overriden when whole bulks needs to be processed at once.
	 * @param message message to process
	 * @return completable future
	 */
	default Future<Void> process(ProcessedData message) {
		JsonArray data = message.getData();
		Future<Void> future = Future.succeededFuture();
		if (!data.isEmpty()) {
			for (Object o : data) {
				JsonObject row = (JsonObject) o;
				future = future.compose((v) -> process(row));
			}
		}
		return future;
	}

	/**
	 * In case action takes long time and is not desired to process another ETL until action completes, tell sender to skip
	 * ETL as action is still in progress
	 * @return true when action is in progress
	 */
	default boolean isActionInProgress() {
		return false;
	}

	final class ProcessedData {
		JsonArray data;
		boolean valid;
		String message;

		public ProcessedData(JsonArray data, boolean valid) {
			this.data = data;
			this.valid = valid;
		}

		public JsonArray getData() {
			return data;
		}

		public void setData(JsonArray data) {
			this.data = data;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

}
