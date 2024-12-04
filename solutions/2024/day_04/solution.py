# puzzle prompt: https://adventofcode.com/2024/day/4

from ...base import StrSplitSolution, answer
from ...utils.tools import *  # noqa: F403


class Solution(StrSplitSolution):
    _year = 2024
    _day = 4

    @answer(2414)
    def part_1(self) -> int:
        count = 0
        maxrow = len(self.input)
        maxcol = len(self.input[0])
        for i in range(maxrow):
            for j in range(maxcol):
                if self.input[i][j] == "X":
                    # horizontal forwards
                    if self.input[i][j:j+4] == "XMAS":
                        count += 1
                    # horizontal backwards
                    if self.input[i][j-3:j+1] == "SAMX":
                        count += 1
                    # vertical forwards
                    if i < maxcol - 3 and self.input[i + 1][j] == "M" and self.input[i + 2][j] == "A" and self.input[i + 3][j] == "S":
                        count += 1
                    # vertical backwards
                    if i > 2 and self.input[i - 1][j] == "M" and self.input[i - 2][j] == "A" and self.input[i - 3][j] == "S":
                        count += 1
                    # diagonal forwards
                    if i < maxcol - 3 and j < len(self.input[0]) - 3 and self.input[i + 1][j + 1] == "M" and self.input[i + 2][j + 2] == "A" and self.input[i + 3][j + 3] == "S":
                        count += 1
                    # diagonal backwards
                    if i > 2 and j > 2 and self.input[i - 1][j - 1] == "M" and self.input[i - 2][j - 2] == "A" and self.input[i - 3][j - 3] == "S":
                        count += 1
                    # reverse diagonal forwards
                    if i < maxcol - 3 and j > 2 and self.input[i + 1][j - 1] == "M" and self.input[i + 2][j - 2] == "A" and self.input[i + 3][j - 3] == "S":
                        count += 1
                    # reverse diagonal backwards
                    if i > 2 and j < len(self.input[0]) - 3 and self.input[i - 1][j + 1] == "M" and self.input[i - 2][j + 2] == "A" and self.input[i - 3][j + 3] == "S":
                        count += 1
        return count

    @answer(1871)
    def part_2(self) -> int:
        count = 0
        maxrow = len(self.input)
        maxcol = len(self.input[0])
        for i in range(maxrow-2):
            for j in range(maxcol-2):
                if self.input[i+1][j+1] == "A":
                    if self.input[i][j] == "M":
                        if self.input[i][j+2] == "M" and self.input[i+2][j] == "S" and self.input[i+2][j+2] == "S":
                            count += 1 
                        if self.input[i][j+2] == "S" and self.input[i+2][j] == "M" and self.input[i+2][j+2] == "S":
                            count += 1
                    if self.input[i][j] == "S":
                        if self.input[i][j+2] == "S" and self.input[i+2][j] == "M" and self.input[i+2][j+2] == "M":
                            count += 1
                        if self.input[i][j+2] == "M" and self.input[i+2][j] == "S" and self.input[i+2][j+2] == "M":
                            count += 1
        return count

