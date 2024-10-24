1. The best solution to the 15-block problem is: <br>
   **Target (optimal) height**: 20.23459830007131 <br>
   **Best subset**: [4, 5, 6, 10, 11, 12, 13] <br>
   **Best height**: 20.23411306140849 <br>
   **Distance from optimal**: 0.0004852386628151351 <br>
<br>
2. For 20 blocks, my program averaged 244 ms in three runs. For 21 blocks, 478.33 ms and for 22 blocks
   1022 ms. Every time I added a block, the runtime doubled (approx.), meaning the runtime complexity
   is likely O(n^2). This makes sense considering the nested loops required to find the best subset
   and height. <br> 
<br>
3. Following this logic, for 50 blocks runtime could be, using the value for 22 blocks:
   1022 * 2^(50-22) = 274,341,036,032 ms = 8.7 years.
