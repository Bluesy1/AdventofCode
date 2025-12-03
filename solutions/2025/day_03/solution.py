# puzzle prompt: https://adventofcode.com/2025/day/3

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2025
    _day = 3

    @answer(17263)
    def part_1(self) -> int:
        part1 = 0
        l = len(self.input[0])
        for line in self.input:
            best = -1
            for i in range(l):
                for j in range(i + 1, l):
                    best = max(best, int(line[i] + line[j]))
                    if best == 99:
                        break
                if best == 99:
                    break
            part1 += best
        return part1

    @answer(170731717900423)
    def part_2(self) -> int:
        part2 = 0
        k = 12
        for line in self.input:
            n = len(line)
            start = 0
            selected = []
            for pos in range(k):
                # must leave (k - pos - 1) chars after the pick, so last pick index is n - (k - pos)
                end = n - (k - pos - 1)
                # find max digit in line[start:end) and its first occurrence
                max_d = '0'
                max_idx = start
                for i in range(start, end):
                    c = line[i]
                    if c > max_d:
                        max_d = c
                        max_idx = i
                        if max_d == '9':
                            break
                selected.append(max_d)
                start = max_idx + 1
            part2 += int("".join(selected))

        return part2
