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
package org.apache.beam.sdk.io.aws.sns;

import static org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.base.Preconditions.checkArgument;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;

/** Basic implementation of {@link AwsClientsProvider} used by default in {@link SnsIO}. */
class BasicSnsProvider implements AwsClientsProvider {

  private final String accessKey;
  private final String secretKey;
  private final Regions region;
  private final @Nullable String serviceEndpoint;

  BasicSnsProvider(
      String accessKey, String secretKey, Regions region, @Nullable String serviceEndpoint) {
    checkArgument(accessKey != null, "accessKey can not be null");
    checkArgument(secretKey != null, "secretKey can not be null");
    checkArgument(region != null, "region can not be null");
    this.accessKey = accessKey;
    this.secretKey = secretKey;
    this.region = region;
    this.serviceEndpoint = serviceEndpoint;
  }

  private AWSCredentialsProvider getCredentialsProvider() {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
  }

  @Override
  public AmazonSNS createSnsPublisher() {
    AmazonSNSClientBuilder clientBuilder =
        AmazonSNSClientBuilder.standard().withCredentials(getCredentialsProvider());
    if (serviceEndpoint == null) {
      clientBuilder.withRegion(region);
    } else {
      clientBuilder.withEndpointConfiguration(
          new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region.getName()));
    }
    return clientBuilder.build();
  }
}
