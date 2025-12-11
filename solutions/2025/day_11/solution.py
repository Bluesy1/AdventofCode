# puzzle prompt: https://adventofcode.com/2025/day/11

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2025
    _day = 11

    @answer((428, 331468292364745))
    def solve(self) -> tuple[int, int]:
        inp = (
                line.replace(":", "").split() for line in self.input
            )
        nodes = {node: set(to) for node, *to in inp}

        @cache
        def part1(start: str):
            if start == "out":
                return 1
            return sum(part1(node) for node in nodes[start])

        @cache
        def part2(start: str, dac: bool, fft: bool) -> int:
            if start == "out":
                return dac and fft

            if start == "dac":
                dac = True
            elif start == "fft":
                fft = True

            return sum(part2(node, dac, fft) for node in nodes[start])
        
        return part1("you"), part2("svr", False, False)
