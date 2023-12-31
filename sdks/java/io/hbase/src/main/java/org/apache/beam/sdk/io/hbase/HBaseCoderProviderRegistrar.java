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
package org.apache.beam.sdk.io.hbase;

import com.google.auto.service.AutoService;
import java.util.List;
import org.apache.beam.sdk.coders.CoderProvider;
import org.apache.beam.sdk.coders.CoderProviderRegistrar;
import org.apache.beam.sdk.coders.CoderProviders;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.collect.ImmutableList;
import org.apache.hadoop.hbase.client.Result;

/** A {@link CoderProviderRegistrar} for standard types used with {@link HBaseIO}. */
@AutoService(CoderProviderRegistrar.class)
public class HBaseCoderProviderRegistrar implements CoderProviderRegistrar {
  @Override
  public List<CoderProvider> getCoderProviders() {
    return ImmutableList.of(
        HBaseMutationCoder.getCoderProvider(),
        HBaseRowMutationsCoder.getCoderProvider(),
        CoderProviders.forCoder(TypeDescriptor.of(Result.class), HBaseResultCoder.of()));
  }
}
