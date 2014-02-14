In the benchmarkClient, I am sending 1000 strings to both the Single threaded and multithreaded 
Server via the socket and then time how long it takes for each of the servers to process the thousand
strings and return them back to the benchmarkClient. I then divide this number by 1000 to get the 
average time it took the server to process the string.

1000 was arbitrarily chosen but I just wanted a large number to get a solid average.

When both servers are started and the BenchmarkClient is run, it will print the average nanoseconds for
each type of server. The numbers differed often by my last run produced the following.

173842 Average Nanoseconds for Single thread Server
148837 Average Nanoseconds for Multithread Server

The Multithreaded is obviously a better server because it can process multiple socket inputs simultaneously as
opposed to the single threaded which has to complete each socket input before moving on.