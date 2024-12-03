# puzzle prompt: https://adventofcode.com/2024/day/3

from ...base import TextSolution, answer
from ...utils.tools import *


class Solution(TextSolution):
    _year = 2024
    _day = 3

    @answer((187825547, 85508223))
    def solve(self) -> tuple[int, int]:
        part1, part2 = 0, 0
        pattern = re.compile(r"do\(\)|don't\(\)|mul\((?P<first>\d+),(?P<second>\d+)\)")
        enabled = True
        for match in pattern.finditer(self.input):
            if match[0] == "do()":
                enabled = True
            elif match[0] == "don't()":
                enabled = False
            else:
                mul = int(match[1]) * int(match[2])
                part1 += mul
                if enabled:
                    part2 += mul
        return part1, part2
