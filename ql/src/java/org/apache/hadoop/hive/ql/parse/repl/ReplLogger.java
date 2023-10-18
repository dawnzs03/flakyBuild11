/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.parse.repl;

import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.hive.ql.exec.repl.NoOpReplStatsTracker;
import org.apache.hadoop.hive.ql.exec.repl.ReplStatsTracker;
import org.apache.hadoop.hive.ql.parse.repl.load.log.state.DataCopyEnd;

/**
 * ReplLogger.
 *
 * Logger class for Repl Events.
 **/
public abstract class ReplLogger<T> {

  public ReplLogger() {
  }

  public abstract void startLog();

  public abstract void endLog(T logVal);

  public void tableLog(String tableName, TableType tableType) {
  }
  public void functionLog(String funcName){
  }
  public void eventLog(String eventId, String eventType) {
  }

  public void dataCopyLog(String message) {
    new DataCopyEnd(message).log(ReplState.LogTag.DATA_COPY_END);
  }

  public void setParams(String dbName, String dumpDirectory, long numTables, long numFunctions) {
  }

  public ReplStatsTracker getReplStatsTracker() {
    return new NoOpReplStatsTracker();
  }
}
