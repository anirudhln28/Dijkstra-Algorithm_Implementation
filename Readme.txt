Created by : Anirudh Lakshminarayanan

"Dijkstra's algorithm, conceived by Dutch computer scientist Edsger Dijkstra, is a graph search algorithm that solves the single-source shortest path problem for a graph with nonnegative edge path costs, producing a shortest path tree. This algorithm is often used in routing and as a subroutine in other graph algorithms."

Project : Graph Building , Manipulations and Dijkstra’s implementation
*/

/*
Programming Language used : Java
Compiler used : JDK 1.8

*/

/*

File name : Graph.java
Execution: java Graph filename
Example : java Graph example.txt

For further manipulation in graph , you can enter one of the following

      1.addedge dest,source
      2.deleteedge dest,source
      3.edgedown dest,source
      4.edgeup dest,source
      5.vertexdown vertex
      6.vertexup vertex
      7.reachable
      8.print
      9.path source dest
      10.quit

Program gets terminated on input quit

*/

/*
Classes used:
1.Graph
2.Edge
3.Vertex
4.MinHeap

*/

/*

Functions in class:

GRAPH:
addedge,deletedge,edgestatus,vertexstatus,reachable,printgraph,dijkstra

MinHeap:-3.

Print Graph Function :

Running time of this algorithm is : v*e [actual algorithm , without sorting]
Running time of this algorithm is : v*(elogv)

ExtractMinimum,BuildHeapify,MinHeapify
*/

/*

Dijkstra’s algorithm is implemented using priority queue and min heap.
Data structure used for implementation are : Array List and Queue
Running Time of Dijkstra's algorithm is : (v+e)*logv

*/

/*

Reachable vertex Algorithm:

First it sorts the vertices and run reachable function on each vertex. Reachable function takes the source vertex,adds it to queue and gets all it adjacent vertices whic are not yet visited,marks those vertices as visited and adds these vertices to queue.Once these steps are finished source vertex is removed from the queue.These steps are then done again to the vertices in the queue.This is continued until the queue is empty.

Data Structures used : Queue, List

Running time of this algorithm is : V(V+E) [Actual algorithm , without sorting]
Running time of this algorithm is : (v*2)logv [with sorting]

*/

/*

Data structures used : Queue,List

*/