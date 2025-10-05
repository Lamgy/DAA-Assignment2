# Boyer–Moore Majority Vote Algorithm
**Assignment 2 – Algorithmic Analysis and Peer Code Review**

---

## Overview
This project implements the **Boyer–Moore Majority Vote Algorithm**, which efficiently finds the element that appears more than ⌊n/2⌋ times in an integer array.  
It is part of **Pair 3: Linear Array Algorithms** for Assignment 2.

**Student Role:** Student A – Boyer–Moore Majority Vote  
**Partner Algorithm:** Kadane’s Algorithm (Student B)

---

## Algorithm Description
The Boyer–Moore algorithm maintains:
- a **candidate** for the majority element,
- and a **count** that increases when the candidate reappears and decreases otherwise.

If `count` drops to zero, a new candidate is chosen.  
At the end, one optional verification pass confirms the candidate actually occurs more than n/2 times.

**Complexity:**
| Metric | Complexity |
|:--------|:------------|
| Time | Θ(n) — single linear scan + optional verification |
| Space | Θ(1) — only constant variables used |
| Best / Average / Worst | All linear in n |

---

## Repository Structure
assignment2-boyermoore/ \
├── src/main/java/\
│ ├── algorithms/BoyerMooreMajorityVote.java\
│ ├── metrics/PerformanceTracker.java\
│ └── cli/BenchmarkRunner.java\
├── src/test/java/\
│ └── algorithms/BoyerMooreMajorityVoteTest.java\
├── docs/\
│ ├── analysis-report.pdf\
│ └── performance-plots/\
├── README.md\
├── pom.xml\
└── .gitignore\
---

##  Building & Running

### Compile
```bash
 mvn clean compile
Run Tests
bash
Копировать код
mvn test
 Run CLI Benchmark
bash
Копировать код
 mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner"