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
package org.apache.hadoop.hive.ql.optimizer.calcite.reloperators;

import java.util.ArrayList;
import java.util.List;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexBiVisitor;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.rex.RexVisitor;
import org.apache.hadoop.hive.ql.plan.ExprNodeColumnListDesc;

/**
 * This class represents the equivalent to {@link ExprNodeColumnListDesc}
 * in a Calcite expression. It is not supposed to be used through planning
 * and should be immediately expanded after it has been generated by
 * the parser.
 */
public class HiveRexExprList extends RexNode {

  final List<RexNode> expressions = new ArrayList<>();

  public void addExpression(RexNode expression) {
    expressions.add(expression);
  }

  public List<RexNode> getExpressions() {
    return expressions;
  }

  @Override
  public RelDataType getType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <R> R accept(RexVisitor<R> visitor) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <R, P> R accept(RexBiVisitor<R, P> visitor, P arg) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof HiveRexExprList) {
      return this.expressions.equals(((HiveRexExprList) obj).expressions);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return expressions.hashCode();
  }

}
