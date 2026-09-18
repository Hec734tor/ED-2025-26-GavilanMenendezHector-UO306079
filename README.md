# Advanced Graph & Pathfinding Algorithms 🗺️ (Java)

A robust framework for modeling complex networks and solving advanced pathfinding problems. This project implements core graph theory data structures and industry-standard routing algorithms from scratch, emphasizing clean Object-Oriented design and mathematical optimization.

## About the Project
Graphs are foundational to solving real-world routing, networking, and logistical challenges. This repository demonstrates the ability to model weighted networks and compute optimal paths efficiently. It features a custom graph architecture governed by strict interfaces and robust error handling through custom-built exceptions.

## Core Algorithms & Features ⚙️

* **Dijkstra's Algorithm:**
  * Implemented single-source shortest path logic to find the most efficient route between nodes in a weighted graph.
  * Managed state and distance tracking using custom data classes (`DijkstraDataClass`).
* **Floyd-Warshall Algorithm:**
  * Engineered the all-pairs shortest path algorithm to compute optimal routes between every pair of nodes simultaneously.
  * Optimized matrix manipulation to handle dynamic path reconstructions.
* **Custom Graph Architecture:**
  * Designed a scalable graph model adhering to a strict `GraphInterface` for modularity.
  * Implemented robust exception handling (`ElementNotPresentException`, `FullStructureException`) to ensure system stability and prevent silent failures.

## Technical Focus
* **Algorithmic Efficiency:** Deep understanding of Big O time complexity for graph traversals and matrix operations (e.g., O(V³) for Floyd-Warshall, optimized Dijkstra).
* **Software Engineering Best Practices:** Strong emphasis on abstraction, interfaces, and defensive programming via custom exception classes.
* **Scalability:** Code structure designed to easily integrate new pathfinding algorithms (like A*) or different graph representations (Adjacency Matrix vs. Adjacency List).

