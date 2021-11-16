package io.stargate.sgv2.restsvc.grpc;

import io.stargate.proto.Schema;
import io.stargate.proto.StargateGrpc;

/**
 * Client for accessing Schema information from Bridge/gRPC service. Initially used by REST
 * operations directly, eventually likely hidden behind caching layer.
 */
public class BridgeSchemaClient {
  private final StargateGrpc.StargateBlockingStub blockingStub;

  protected BridgeSchemaClient(StargateGrpc.StargateBlockingStub blockingStub) {
    this.blockingStub = blockingStub;
  }

  public static BridgeSchemaClient create(StargateGrpc.StargateBlockingStub blockingStub) {
    return new BridgeSchemaClient(blockingStub);
  }

  public Schema.CqlTable findTable(String keyspace, String tableName) {
    final Schema.DescribeTableQuery descTableQuery =
        Schema.DescribeTableQuery.newBuilder()
            .setKeyspaceName(keyspace)
            .setTableName(tableName)
            .build();
    final Schema.CqlTable table = blockingStub.describeTable(descTableQuery);
    return table;
  }
}
