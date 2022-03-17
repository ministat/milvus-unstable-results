# Setup env

Follow the steps in https://github.com/milvus-io/bootcamp/blob/master/benchmark_test/lab2_sift1b_100m.md to create collection, and build index.

# Build this app

mvn clean package

# Run the following command:

java -jar target/milvus-benchmark-1.0-SNAPSHOT.jar -a QUICKSEARCH -c ann_100m_sq8

The output randomly gives different result:

```
Search time cost: 477ms
Search result of No.0
[(ID: 87356234 Score: 0.94079435), (ID: 344333 Score: 0.9679534), (ID: 64429592 Score: 0.9938071), (ID: 8430679 Score: 1.0020553), (ID: 40642738 Score: 1.030692), (ID: 18530806 Score: 1.0386481), (ID: 80468484 Score: 1.0398453), (ID: 22664991 Score: 1.0444247), (ID: 53179241 Score: 1.0483937), (ID: 21192937 Score: 1.0600007), (ID: 88762980 Score: 1.0619603), (ID: 14925721 Score: 1.0722969), (ID: 99117874 Score: 1.080706), (ID: 13934280 Score: 1.0825374), (ID: 72027288 Score: 1.0837624), (ID: 97814940 Score: 1.084217), (ID: 13105118 Score: 1.090414), (ID: 97950492 Score: 1.0904853), (ID: 60066306 Score: 1.091538), (ID: 18760679 Score: 1.0933377)]
……
Search time cost: 334ms
Search result of No.0
[(ID: 504814 Score: 0.9377404), (ID: 87356234 Score: 0.94079435), (ID: 344333 Score: 0.9679534), (ID: 28539420 Score: 0.98356724), (ID: 64429592 Score: 0.9938071), (ID: 8430679 Score: 1.0020553), (ID: 40642738 Score: 1.030692), (ID: 9295796 Score: 1.0313499), (ID: 18530806 Score: 1.0386481), (ID: 80468484 Score: 1.0398453), (ID: 22664991 Score: 1.0444247), (ID: 53179241 Score: 1.0483937), (ID: 21192937 Score: 1.0600007), (ID: 88762980 Score: 1.0619603), (ID: 82631435 Score: 1.0677121), (ID: 14925721 Score: 1.0722969), (ID: 95601336 Score: 1.0781072), (ID: 99117874 Score: 1.080706), (ID: 21686232 Score: 1.0807402), (ID: 61457390 Score: 1.0821154)]
```
