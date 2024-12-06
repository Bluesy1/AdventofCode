# puzzle prompt: https://adventofcode.com/2024/day/6

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2024
    _day = 6

    @answer((5318, 1831))
    def solve(self) -> tuple[int, int]:
        dirs = [(-1, 0), (0, 1), (1, 0), (0, -1)]
        p1 = 0
        p2 = 0
        matrix = [list(row) for row in self.input]
        rows = len(matrix)
        cols = len(matrix[0])
        for i in range(rows):
            for j in range(cols):
                if matrix[i][j] == "^":
                    initial_i, initial_j = i, j
                    break

        for ii in range(rows):
            for jj in range(cols):
                i, j = initial_i, initial_j
                direction = 0  # 0=up, 1=right, 2=down, 3=left
                seen = set()
                seen2 = set()
                while True:
                    if (i, j, direction) in seen:
                        p2 += 1
                        break
                    seen.add((i, j, direction))
                    seen2.add((i, j))
                    di, dj = dirs[direction]
                    rr = i + di
                    cc = j + dj
                    if not (0 <= rr < rows and 0 <= cc < cols):
                        if matrix[ii][jj] == "#":
                            p1 = len(seen2)
                        break
                    if matrix[rr][cc] == "#" or rr == ii and cc == jj:
                        direction = (direction + 1) % 4
                    else:
                        i = rr
                        j = cc
        return p1, p2
