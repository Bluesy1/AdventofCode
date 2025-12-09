# puzzle prompt: https://adventofcode.com/2025/day/9

import itertools

from shapely.geometry import Polygon

from ...base import StrSplitSolution, answer
from ...utils.tools import *

@cache
def area(x1, y1, x2, y2):
    return (abs(x1 - x2) + 1) * (abs(y1 - y2) + 1)

class Solution(StrSplitSolution):
    _year = 2025
    _day = 9

    @answer((4749672288, 1479665889))
    def solve(self) -> tuple[int, int]:
        nodes: list[Point2D] = [tuple(nums(box)) for box in self.input]  # pyright: ignore[reportAssignmentType]
        poly = Polygon(nodes)

        boxes = sorted(
            itertools.combinations(nodes, 2),
            key=lambda pair: area(pair[0][0], pair[0][1], pair[1][0], pair[1][1]),
            reverse=True,
        )

        part1 = next(area(x1, y1, x2, y2) for ((x1, y1), (x2, y2)) in boxes)
        
        part2 = next(
            area(x1, y1, x2, y2)
            for ((x1, y1), (x2, y2)) in boxes
            if poly.covers(Polygon([(x1, y1), (x1, y2), (x2, y2), (x2, y1)]))
        )
        return part1, part2
