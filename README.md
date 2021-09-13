A repository to store work for our testing framework for dynamic graph program analysis.

# Testing framework for dynamic graph program analysis
​

Team 9

Josue Caraballo​, Dinesh Madurantakam​, Yu Zhao

About the program

Programs that operate on graph database data require reliable methods that establish the connection to, read, write and process data.​
Dynamic testing of these programs includes simulation of a graph database in various states to ensure code that is tested is robust to handle various edge cases such as empty graphs, graphs with heterogeneous data, etc.​
Additionally, target query metrics can be specified to validate program adherence to graph behavior specifications with the results being visualized.​
Why dynamically test graph programs?​

Programs that are relied on to populate or manipulate graphs in specific ways need to perform expectedly and not fail due to a lack of proper edge case handling.​
Although static program analysis can identify certain problems in algorithm implementation, dynamic analysis can identify different problems given a set of constraints that are expected to be met during program execution.
Why create a testing framework?

Companies use software development frameworks to reduce boilerplate and increase developer efficiency, extending these existing frameworks to provide additional testing functionality allows us to benefit those who want to add our framework to their project.​
Contributing to the plugin can also serve as a reference for other developers in the future for examples relating to graph test-case generation, graph database instrumentation, and metric collection can be implemented.​
Project Domain​

For testing, we will simulate graph programs a fintech startup may be interested in.​

Program to populate graph with stock data.​
Program to populate graph with simulated investors.​
Program to execute market & buy limit orders on a graph.​
Program to update graph stock price data.


Programming Language​ & Testing framework​

The language we are all familiar with is Java, so we will use that.​
We will use the maven software development framework to build & test our software.​
We will extend the JUnit testing framework with this software to enable dynamic graph testing.​
Functions we need to create

Turn graph on and off
Register queries as metrics and tracks them over test execution – we can use a config file to specify these so our framework loads them in at runtime
'Metric queries' should have the metric values plotted and attached to the test HTML report.
The graph should be visualized at different stages during the testing process and these images should be added to the test HTML report.
Be able to run the program independently of the test with whatever parameters the program needs to run. Needs to be resistant to program failure (test should indicate that the program under test failed instead of crashing).
Different roles in the system

For stocks:
We need the stock symbol, the time the price was last measured, and the price. These sources claim to offer free API keys that are rate-limited. If we operate within the rates we can use these as free data sources for stock data. Or parsing historical data from some other source.

https://polygon.io/docs/getting-started

https://www.alphavantage.co/documentation/

https://marketstack.com/

For market or buy limit orders:
We need the order DateTime, fulfillment DateTime (if fulfilled), target share price, number of shares, and order status. These items can be generated.

For investors:
We need their name and the uninvested balance of their account.
