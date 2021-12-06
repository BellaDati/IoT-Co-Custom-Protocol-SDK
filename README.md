# Belladati IOT Controller API

## Receiver Endpoint

### Implementation

ReceiverEndpoint interface is provided to support implementation of custom
protocol processing. One can implement connection to any device via any protocol
and handle received data to a provided instance of a message processor.

`java.util.ServiceLoader` is used to load this implementation into the receiver. Basically,
all that needs to be done is to create a file 
`META-INF/services/com.belladati.iot.collector.generic.receiver.endpoint.ReceiverEndpoint`
that would contain fully qualified name of your implementation class, e.g.
```
com.belladati.iot.ext.MyCustomEndpointImpl
```

### Execution

Once this extension is bundled in a single JAR with all its dependencies (or you may
add the dependencies to the classpath), the receiver can be started with altered command
that would look like this
```
java -cp iot-collector-generic-receiver-prod.jar;iot-ext-1.0-SNAPSHOT-jar-with-dependencies.jar com.belladati.iot.collector.common.BellaDatiLauncher -Dapplication.name=receiver1
```
instead of traditional `java -jar`.

