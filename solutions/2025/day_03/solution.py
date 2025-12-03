# puzzle prompt: https://adventofcode.com/2025/day/3

from ...base import StrSplitSolution, answer
from ...utils.tools import *

def score(nums: str, k: int) -> int:
    n = len(nums)
    start = 0
    selected = []
    for pos in range(k):
        # must leave (k - pos - 1) chars after the pick, so last pick index is n - (k - pos)
        end = n - (k - pos - 1)
        # find max digit in line[start:end) and its first occurrence
        max_d = '0'
        max_idx = start
        for i in range(start, end):
            c = nums[i]
            if c > max_d:
                max_d = c
                max_idx = i
                if max_d == '9':
                    break
        selected.append(max_d)
        start = max_idx + 1
    return int("".join(selected))


class Solution(StrSplitSolution):
    _year = 2025
    _day = 3

    @answer((17263, 170731717900423))
    def solve(self) -> tuple[int, int]:
        part1 = part2 = 0
        for line in self.input:
            part1 += score(line, 2)
            part2 += score(line, 12)
        return part1, part2
