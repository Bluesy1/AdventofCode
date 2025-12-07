# puzzle prompt: https://adventofcode.com/2025/day/6

import functools
from operator import add, mul

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2025
    _day = 6

    @answer((4805473544166, 8907730960817))
    def solve(self) -> tuple[int, int]:
        *lines, ops = self.input
        ops = [mul if op == "*" else add for op in ops.replace(" ", "")]
        l = len(lines[0])
        sets: list[tuple[str, ...]] = []
        start = 0
        for i in range(l):
            if all(line[i] == " " for line in lines):
                sets.append(tuple(line[start:i] for line in lines))
                start = i + 1
        sets.append(tuple(line[start:] for line in lines))
        part1 = sum(
            functools.reduce(op, map(int, nums))
            for op, nums in zip(ops, sets)
        )
        part2 = sum(
                    functools.reduce(op, (int("".join(s for s in i if s != ' ')) for i in itertools.zip_longest(*nums)))
                    for op, nums in zip(ops, sets)
                )
        return part1, part2
