---
title: "Filter"
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

# Filter

{{< localstorage language language-py >}}

{{< button-pydoc path="apache_beam.transforms.core" class="Filter" >}}

Given a predicate, filter out all elements that don't satisfy that predicate.
May also be used to filter based on an inequality with a given value based
on the comparison ordering of the element.

## Examples

In the following examples, we create a pipeline with a `PCollection` of produce with their icon, name, and duration.
Then, we apply `Filter` in multiple ways to filter out produce by their duration value.

`Filter` accepts a function that keeps elements that return `True`, and filters out the remaining elements.

### Example 1: Filtering with a function

We define a function `is_perennial` which returns `True` if the element's duration equals `'perennial'`, and `False` otherwise.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_FilterFunction" show="filter_function" >}}
{{< /playground >}}

### Example 2: Filtering with a lambda function

We can also use lambda functions to simplify **Example 1**.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_FilterLambda" show="filter_lambda" >}}
{{< /playground >}}

### Example 3: Filtering with multiple arguments

You can pass functions with multiple arguments to `Filter`.
They are passed as additional positional arguments or keyword arguments to the function.

In this example, `has_duration` takes `plant` and `duration` as arguments.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_FilterMultipleArguments" show="filter_multiple_arguments" >}}
{{< /playground >}}

### Example 4: Filtering with side inputs as singletons

If the `PCollection` has a single value, such as the average from another computation,
passing the `PCollection` as a *singleton* accesses that value.

In this example, we pass a `PCollection` the value `'perennial'` as a singleton.
We then use that value to filter out perennials.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_FilterSideInputsSingleton" show="filter_side_inputs_singleton" >}}
{{< /playground >}}

### Example 5: Filtering with side inputs as iterators

If the `PCollection` has multiple values, pass the `PCollection` as an *iterator*.
This accesses elements lazily as they are needed,
so it is possible to iterate over large `PCollection`s that won't fit into memory.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_FilterSideInputsIter" show="filter_side_inputs_iter" >}}
{{< /playground >}}

> **Note**: You can pass the `PCollection` as a *list* with `beam.pvalue.AsList(pcollection)`,
> but this requires that all the elements fit into memory.

### Example 6: Filtering with side inputs as dictionaries

If a `PCollection` is small enough to fit into memory, then that `PCollection` can be passed as a *dictionary*.
Each element must be a `(key, value)` pair.
Note that all the elements of the `PCollection` must fit into memory for this.
If the `PCollection` won't fit into memory, use `beam.pvalue.AsIter(pcollection)` instead.

{{< playground height="700px" >}}
{{< playground_snippet language="py" path="SDK_PYTHON_FilterSideInputsDict" show="filter_side_inputs_dict" >}}
{{< /playground >}}

## Related transforms

* [FlatMap](/documentation/transforms/python/elementwise/flatmap) behaves the same as `Map`, but for
  each input it might produce zero or more outputs.
* [ParDo](/documentation/transforms/python/elementwise/pardo) is the most general elementwise mapping
  operation, and includes other abilities such as multiple output collections and side-inputs.

{{< button-pydoc path="apache_beam.transforms.core" class="Filter" >}}
