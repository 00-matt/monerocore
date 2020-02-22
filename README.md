# monerocore

Pure Java Monero utilities.

## Project goals

This project was initially created to support a mining pool without
any native code.

## Installation

(note: requires Java 11)

### Maven

```
<dependency>
    <groupId>uk.offtopica</groupId>
    <artifactId>monerocore</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```
compile group: 'uk.offtopica', name: 'monerocore', version: '0.1.0'
```


## Example

```java
import uk.offtopica.monerocore.BlockHashingBlobCreator;
import uk.offtopica.monerorpc.daemon.BlockTemplate;
import uk.offtopica.monerorpc.daemon.MoneroDaemonRpcClient;

// ...

// Block blob can come from anywhere; using monerorpc for example.
final var daemon =
    new MoneroDaemonRpcClient(URI.create("localhost:18081/json_rpc"));
final var blockTemplate = daemon.getBlockTemplate("41zih...", 8);
final byte[] blob = blockTemplate.getBlockTemplateBlob();

// Can be re-used.
final var bhbc = new BlockHashingBlobCreator();

// Mine on this.
final byte[] hashingBlob = bbhc.getHashingBlob(blob);
```

## See Also

Goes great with [monerorpc](https://www.offtopica.uk/projects/monerorpc).

## License

Released under the terms of the MIT license.
See [LICENSE](LICENSE) for more information.
