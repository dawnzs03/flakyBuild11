EXPLAIN CBO SELECT 1;
EXPLAIN FORMATTED CBO SELECT 1;
EXPLAIN SELECT 1;
EXPLAIN FORMATTED SELECT 1;

set hive.cbo.rule.exclusion.regex=HiveJoinPushTransitivePredicatesRule|HivePreFilteringRule;

EXPLAIN CBO SELECT 1;
EXPLAIN FORMATTED CBO SELECT 1;
EXPLAIN SELECT 1;
EXPLAIN FORMATTED SELECT 1;
