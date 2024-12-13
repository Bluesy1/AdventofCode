# puzzle prompt: https://adventofcode.com/2024/day/13

from ...base import TextSolution, answer
from ...utils.tools import *


class Solution(TextSolution):
    _year = 2024
    _day = 13

    @answer((34393, 83551068361379))
    def solve(self) -> tuple[int, int]:
        games = self.input.split("\n\n")
        p1 = p2 = 0

        for game in games:
            x1, y1, x2, y2, target_x, target_y = nums(game)
            a = (target_x*y2 - target_y*x2) / (x1*y2 - y1*x2)
            b = (target_y*x1 - target_x*y1) / (x1*y2 - y1*x2)
            if a == int(a) and b == int(b):
                p1 += (3 * a) + b
            target_x += 10000000000000
            target_y += 10000000000000
            a = (target_x*y2 - target_y*x2) / (x1*y2 - y1*x2)
            b = (target_y*x1 - target_x*y1) / (x1*y2 - y1*x2)
            if a == int(a) and b == int(b):
                p2 += (3 * a) + b

        return int(p1), int(p2)
