# Generated using @xavdid's AoC Python Template: https://github.com/xavdid/advent-of-code-python-template

# puzzle prompt: https://adventofcode.com/2024/day/2

from itertools import pairwise

from ...base import StrSplitSolution, answer


def is_valid(seq):
    return all(1 <= a-b <= 3 for a,b in pairwise(seq)) or all(1 <= b-a <= 3 for a,b in pairwise(seq))

class Solution(StrSplitSolution):
    _year = 2024
    _day = 2

    @answer((282, 349))
    def solve(self) -> tuple[int, int]:
        seqs = [list(map(int, line.split())) for line in self.input]
        part1 = 0
        part2 = 0
        for row in seqs:
            if is_valid(row):
                part1 += 1
                part2 += 1
            elif any(is_valid(row[:i] + row[i+1:]) for i in range(len(row))):
                # Test to see if removing any single element makes the list valid
                part2 += 1
        return part1, part2
