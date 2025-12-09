# puzzle prompt: https://adventofcode.com/2025/day/9

import itertools

from shapely.geometry import Polygon

from ...base import StrSplitSolution, answer
from ...utils.tools import *

type Node = tuple[int, int]

class Solution(StrSplitSolution):
    _year = 2025
    _day = 9

    @answer((4749672288, 1479665889))
    def solve(self) -> tuple[int, int]:
        nodes: list[Node] = [tuple(nums(box)) for box in self.input]  # pyright: ignore[reportAssignmentType]
        poly = Polygon(nodes)

        @cache
        def area(x1, y1, x2, y2):
            return (abs(x1 - x2) + 1) * (abs(y1 - y2) + 1)

        part1 = max(
            area(x1, y1, x2, y2)
            for ((x1, y1), (x2, y2)) in itertools.combinations(nodes, 2)
        )
        part2 = max(
            area(x1, y1, x2, y2)
            for ((x1, y1), (x2, y2)) in itertools.combinations(nodes, 2)
            if poly.covers(Polygon([(x1, y1), (x1, y2), (x2, y2), (x2, y1)]))
        )
        return part1, part2
