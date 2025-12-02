# puzzle prompt: https://adventofcode.com/2025/day/2

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2025
    _day = 2
    separator = ","

    @answer((24043483400, 38262920235))
    def solve(self) -> tuple[int, int]:
        
        input = self.input
        part1 = part2 = 0
        for product_range in input:
            first, last = nums(product_range.replace("-", " "))
            for product in range(first, last+1):
                product_str = str(product)
                l = len(product_str)
                half = l // 2
                for i in range(1, half+1):
                    if l % i != 0:
                        continue
                    for j in range(0, l, i):
                        if product_str[j:j+i] != product_str[(j+i)%(l):(j+i)%(l)+i]:
                            break
                    else:
                        part2 += product
                        break
                if l % 2 == 1:
                    continue
                if product_str[:half] == product_str[half:]:
                    part1 += product
        return part1, part2