# puzzle prompt: https://adventofcode.com/2024/day/7

import functools
from operator import add, mul

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2024
    _day = 7

    @answer(20281182715321)
    def part_1(self) -> int:
        ans = 0
        ops = (add, mul)
        for line in self.input:
            target, *inps = nums(line)
            if functools.reduce(add, inps) == target:
                ans += target
                continue
            if functools.reduce(mul, inps) == target:
                ans += target
                continue

            # try all possible combinations of operations
            tmp = {inps[0]}
            for i in range(1, len(inps)):
                tmp = {op(t, inps[i]) for op in ops for t in tmp}
            if target in tmp:
                ans += target
        return ans

    @answer(159490400628354)
    def part_2(self) -> int:
        ans = 0
        ops = (add, mul, lambda a, b: int(f"{a}{b}"))
        for line in self.input:
            target, *inps = nums(line)
            if functools.reduce(add, inps) == target:
                ans += target
                continue
            if functools.reduce(mul, inps) == target:
                ans += target
                continue

            # try all possible combinations of operations
            tmp = {inps[0]}
            for i in range(1, len(inps)):
                tmp = {op(t, inps[i]) for op in ops for t in tmp}
            if target in tmp:
                ans += target
        return ans
