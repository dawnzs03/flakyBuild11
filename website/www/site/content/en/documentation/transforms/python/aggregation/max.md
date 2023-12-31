---
title: "Max"
---
<!--
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# Max

{{< localstorage language language-py >}}

{{< button-pydoc path="apache_beam.transforms.core" class="CombineGlobally" >}}

Gets the element with the maximum value within each aggregation.

## Examples

In the following example, we create a pipeline with a `PCollection`.
Then, we get the element with the maximum value in different ways.

### Example 1: Maximum element in a PCollection

We use `Combine.Globally()` to get the maximum element from the *entire* `PCollection`.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_MaxGlobally" show="max_globally" >}}
{{< /playground >}}

### Example 2: Maximum elements for each key

We use `Combine.PerKey()` to get the maximum element for each unique key in a `PCollection` of key-values.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_MaxPerKey" show="max_per_key" >}}
{{< /playground >}}

## Related transforms

* [CombineGlobally](/documentation/transforms/python/aggregation/combineglobally)
* [CombinePerKey](/documentation/transforms/python/aggregation/combineperkey)
* [Mean](/documentation/transforms/python/aggregation/mean)
* [Min](/documentation/transforms/python/aggregation/min)
* [Sum](/documentation/transforms/python/aggregation/sum)

{{< button-pydoc path="apache_beam.transforms.core" class="CombineGlobally" >}}
