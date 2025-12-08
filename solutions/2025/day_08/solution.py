# puzzle prompt: https://adventofcode.com/2025/day/8

import math

import networkx

from ...base import StrSplitSolution, answer
from ...utils.tools import *

type Node = tuple[int, int, int]


class Solution(StrSplitSolution):
    _year = 2025
    _day = 8

    @answer((103488, 8759985540))
    def solve(self) -> tuple[int, int]:
        boxes: list[Node] = [tuple(nums(box)) for box in self.input]  # pyright: ignore[reportAssignmentType]

        distances: deque[tuple[float, tuple[Node, Node]]] = deque()

        l = len(boxes)

        graph = networkx.Graph()
        for i in range(l):
            node1 = boxes[i]
            graph.add_node(node1)
            for j in range(i + 1, l):
                node2 = boxes[j]
                distances.append(
                    (
                        math.sqrt(
                            (node1[0] - node2[0]) ** 2
                            + (node1[1] - node2[1]) ** 2
                            + (node1[2] - node2[2]) ** 2
                        ),
                        (node1, node2),
                    )
                )

        distances = deque(sorted(distances))

        iters = 0
        part1 = 0

        while not networkx.is_connected(graph):
            iters += 1
            while True:
                _, (node1, node2) = distances.popleft()
                if node1 not in graph.neighbors(node2):
                    graph.add_edge(node1, node2)
                    break
            if iters == 1000:
                connected = (
                    len(c)
                    for c in sorted(
                        networkx.connected_components(graph), key=len, reverse=True
                    )
                )
                part1 = next(connected) * next(connected) * next(connected)

        return part1, node1[0] * node2[0]
