# puzzle prompt: https://adventofcode.com/2025/day/8

import itertools
import math

from ...base import StrSplitSolution, answer
from ...utils.tools import *

type Node = tuple[int, int, int]


def geo3D(node1: Node, node2: Node, /) -> float:
    return (
        (node1[0] - node2[0]) ** 2
        + (node1[1] - node2[1]) ** 2
        + (node1[2] - node2[2]) ** 2
    )


class Solution(StrSplitSolution):
    _year = 2025
    _day = 8

    @answer((103488, 8759985540))
    def solve(self) -> tuple[int, int]:
        boxes: list[Node] = [tuple(nums(box)) for box in self.input]  # pyright: ignore[reportAssignmentType]
        part1_iters = 10 if self.use_test_data else len(boxes)
        circuits: list[set[tuple[int, int, int]]] = [{box} for box in boxes]

        connections = deque(
            sorted(itertools.combinations(boxes, 2), key=lambda pair: geo3D(*pair))
        )

        iters = 0
        part1 = 0

        for box1, box2 in connections:
            circuit1 = next(c for c in circuits if box1 in c)
            circuit2 = next(c for c in circuits if box2 in c)
            iters += 1
            if circuit1 is circuit2:
                continue
            circuit1 |= circuit2
            circuit2.clear()
            circuits.remove(circuit2)
            if iters == part1_iters:
                connected = sorted((len(c) for c in circuits), reverse=True)
                part1 = math.prod(connected[:3])

            if len(circuits) == 1:
                break

        return part1, box1[0] * box2[0]
