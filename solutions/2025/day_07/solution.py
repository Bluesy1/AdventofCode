# puzzle prompt: https://adventofcode.com/2025/day/7

from typing_extensions import Literal
from ...base import StrSplitSolution, answer
from ...utils.tools import *

import functools

class Solution(StrSplitSolution):
    _year = 2025
    _day = 7

    @answer(1590)
    def part_1(self) -> int:
        splits = 0
        cols: set[int] = {self.input[0].find("S")}
        maxlen = len(self.input[0])
        for row in self.input[1:]:
            # self.debug(cols)
            new_cols = set()
            for c in cols:
                if row[c] == "^":
                    if c > 1:
                        new_cols.add(c-1)
                    if c < maxlen:
                        new_cols.add(c+1)
                    splits += 1
                else:
                    new_cols.add(c)
            cols = new_cols
        return splits

    @functools.cache
    def rec_solve(self, row: int, col: int) -> int:
        is_split = self.input[row][col] == "^"
        if row == len(self.input)-1:
            if is_split:
                return 2
            return 1

        if is_split:
            paths = 0
            if col > 1:
                paths += self.rec_solve(row+1, col-1)

            if col < len(self.input[0])-1:
                paths += self.rec_solve(row+1, col+1)

            return paths
        
        return self.rec_solve(row+1, col)

    @answer(20571740188555)
    def part_2(self) -> int:
        return self.rec_solve(1, self.input[0].find("S")) + 1
