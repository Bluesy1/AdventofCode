# puzzle prompt: https://adventofcode.com/2024/day/11

from functools import cache

from ...base import TextSolution, answer
from ...utils.tools import *


@cache
def eventually(stone, iters) -> int:
    if iters == 0:
        return 1
    
    if stone == 0:
        return eventually(1, iters-1)

    if (stone_len := len(stone_str := str(stone))) % 2 == 0:
        return eventually(int(stone_str[:stone_len//2]), iters-1) + eventually(int(stone_str[stone_len//2:]), iters-1)

    return eventually(stone * 2024, iters-1)


class Solution(TextSolution):
    _year = 2024
    _day = 11

    @answer((217443, 257246536026785))
    def solve(self) -> tuple[int, int]:
        stones = nums(self.input)
        p1 = sum(eventually(stone, 25) for stone in stones)
        p2 = sum(eventually(stone, 75) for stone in stones)
        return p1, p2
