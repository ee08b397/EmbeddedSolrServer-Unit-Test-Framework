[![Build Status](https://travis-ci.org/ee08b397/EmbeddedSolrServer-Unit-Test-Framework.svg?branch=master)](https://travis-ci.org/ee08b397/EmbeddedSolrServer-Unit-Test-Framework)

# EmbeddedSolrServer Unit Test Framework

A way to unit test Solr read and write query syntax. 

* Solr core and SolrJ 4.10.4
* JDK 1.8+ (lambda)
* Maven project
* Multi-core support

## Sample Data

Yahoo finance real time data. 
https://developer.yahoo.com/yql/console/?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22YHOO%22%2C%22AAPL%22%2C%22GOOG%22%2C%22MSFT%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys#h=select+*+from+yahoo.finance.quote+where+symbol+in+(%22CRM%22%2C%22AAPL%22%2C%22GOOG%22%2C%22MSFT%22%2C+%22AMZN%22%2C+%22FB%22%2C+%22YELP%22%2C+%22SNAP%22%2C+%22TSLA%22%2C+%22TWTR%22)