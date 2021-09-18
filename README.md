# Project Scope

We are creating a re-usable plugin for testing software that manipulates Neo4J graphs.

The plugin will be published on the internet for others to use.

The plugin will accept graph test cases specified by the user as [dot language](https://dreampuf.github.io/GraphvizOnline/#digraph%20G%20%7B%0A%20%20Investor_Name_Dinesh%5B%5D%3B%0A%20%20Stock_Symbol_AAPL%5B%5D%3B%0A%20%20Investor_Name_Dinesh%20-%3E%20Stock_Symbol_AAPL%3B%0A%7D).



The plugin will connect to an existing Neo4J database instance and manipulate the graph into the format described by the user.

The plugin will generating the necessary [cypher query statements](https://neo4j.com/developer/cypher/updating/) transfer the input graphs onto the database.

The plugin will then
