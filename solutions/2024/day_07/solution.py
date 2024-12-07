# puzzle prompt: https://adventofcode.com/2024/day/7

import functools
import itertools
from operator import add, mul
from typing import Callable

from ...base import StrSplitSolution, answer
from ...utils.tools import *


def eval_(target: int, operands: list[int], operations: list[Callable[[int, int], int]]) -> int:
    ret = operands[0]
    for operand, op in zip(operands[1:], operations):
        ret = op(ret, operand)
        if ret > target:
            return -1
    return ret

class Solution(StrSplitSolution):
    _year = 2024
    _day = 7

    @answer((20281182715321, 159490400628354))
    def solve(self) -> tuple[int, int]:
        p1 = p2 = 0
        for line in self.input:
            target, *inps = nums(line)
            if target in map(functools.partial(eval_, target, inps), itertools.product((add, mul), repeat=len(inps)-1)):
                p1 += target
                p2 += target
            elif target in map(functools.partial(eval_, target, inps), itertools.product((add, mul, lambda a, b: int(f"{a}{b}")), repeat=len(inps)-1)):
                p2 += target
        return p1, p2