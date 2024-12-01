# Generated using @xavdid's AoC Python Template: https://github.com/xavdid/advent-of-code-python-template

# puzzle prompt: https://adventofcode.com/2024/day/1

from collections import Counter

from ...base import StrSplitSolution, answer


class Solution(StrSplitSolution):
    _year = 2024
    _day = 1

    @answer(3574690)
    def part_1(self) -> int:
        data = [line.split("   ") for line in self.input]
        return sum([abs(a-b) for a,b in zip(sorted(int(i) for i,_ in data), sorted(int(i) for _,i in data))])

    @answer(22565391)
    def part_2(self) -> int:
        data = [line.split("   ") for line in self.input]
        counts = Counter(i for _,i in data)
        return sum(int(i) * counts.get(i, 0) for i,_ in data)

