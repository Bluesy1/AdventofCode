# puzzle prompt: https://adventofcode.com/2025/day/12

from ...base import TextSolution, answer
from ...utils.tools import *

class Solution(TextSolution):
    _year = 2025
    _day = 12

    @answer(575)
    def part_1(self) -> int:
        inp = self.input.split("\n\n")
        valid_regions = 0
        for line in inp[-1].splitlines():
            width, height, *counts = nums(line)
            width = width // 3
            height = height // 3
            space = width * height
            valid_regions += space >= sum(counts)

        return valid_regions

    def part_2(self) -> None:
        return None