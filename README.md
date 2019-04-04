# tgr

## Prerequisites
- `brew install cassandra`
- `./cassandra`

## Loading Cassandra with product data
_Requires cqlsh which should've been installed with cassandra_
`gradle initCassandra`

## Run Application
- `gradle run`

## Fetch product price
`curl "http://localhost:5050/api/products/17098823"`

## Set product price
`curl -H 'Content-Type: application/json' -X PUT -d "{\"value\":2.99, \"currency\":\"USD\"}" "http://localhost:5050/api/products/12345"`

## TODOs
- understand better how to enforce non-null values so don't need elvis
- better handling of configuration
- configure and use external cassandra
- make put json?
- hook up to CI
- etc