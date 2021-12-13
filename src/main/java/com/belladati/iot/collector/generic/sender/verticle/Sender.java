package com.belladati.iot.collector.generic.sender.verticle;

import io.vertx.core.Vertx;

public interface Sender {

    /**
     * Prefix used for logging, it's basically id+": "
     * @return prefix
     */
    String prefix();

    /**
     * Verticle ID, which is "SENDER "name""
     * @return verticle id
     */
    String getId();

    /**
     * Verticle unique name
     * @return name
     */
    String getName();

    /**
     * Name of a receiver this sender is connected with
     * @return receiver name
     */
    String getReceiver();

    /**
     *
     * @return VertX instance
     */
    Vertx getVertx();

}
