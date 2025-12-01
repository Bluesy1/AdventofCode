# puzzle prompt: https://adventofcode.com/2025/day/1

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2025
    _day = 1

    @answer((1097, 7101))
    def solve(self) -> tuple[int, int]:
        input_data = self.input
        dial = 50
        part1 = part2 = 0
        for line in input_data:
            direction = line[0]
            amount = int(line[1:])
            self.debug(f"{dial=} {line=}")
            previous = dial
            if direction == "L":
                dial -= amount
            else:
                dial += amount
            
            if dial == 0:
                part1 += 1

            if previous < dial:
                part2 += abs(previous//100 - dial//100)
            elif previous > dial:
                part2 += abs((-previous)//100 - (-dial)//100)

            
            self.debug(trailing_newline=True)

        return part1, part2
