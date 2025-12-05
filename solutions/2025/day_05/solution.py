# puzzle prompt: https://adventofcode.com/2025/day/5

from ...base import TextSolution, answer
from ...utils.tools import *


class Solution(TextSolution):
    _year = 2025
    _day = 5

    @answer((613, 336495597913098))
    def solve(self) -> tuple[int, int]:
        ranges, ingredients = self.input.split("\n\n")
        available: list[tuple[int, int]] = []
        for line in ranges.splitlines():
            start, stop = nums(line.replace("-", " "))
            available.append((start, stop))

        available.sort()

        merged: list[tuple[int, int]] = []
        current_start, current_end = available[0]
        for start, end in available[1:]:
            if start <= current_end:
                current_end = max(current_end, end)
            else:
                merged.append((current_start, current_end))
                current_start, current_end = start, end
        merged.append((current_start, current_end))
        
        part1 = part2 = 0

        for item in nums(ingredients):
            part1 += any(
                min <= item <= max for min, max in merged
            )

        part2 = 0
        for start, end in merged:
            part2 += end - start + 1

        return part1, part2
