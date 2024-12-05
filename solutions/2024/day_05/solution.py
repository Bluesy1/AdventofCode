# puzzle prompt: https://adventofcode.com/2024/day/5

from ...base import TextSolution, answer
from ...utils.tools import *


class Solution(TextSolution):
    _year = 2024
    _day = 5

    @answer((5747, 5502))
    def solve(self) -> tuple[int, int]:
        orders, pages = self.input.split("\n\n")
        must_after = defaultdict(set)
        for ordering in orders.splitlines():
            before, after = nums(ordering)
            must_after[after].add(before)
        part1 = part2 = 0
        for group_ in pages.splitlines():
            group = nums(group_)
            group_set = set(group)
            valid = True
            for i, page in enumerate(group):
                for before in must_after[page]:
                    if before not in group[:i] and before in group_set:
                        valid = False
                        break
                if valid is False:
                    break
            if valid:
                # add middle number to answer
                part1 += group[len(group) // 2]
            else:
                while not valid:
                    group.remove(before)
                    need_before = must_after[before] & group_set
                    if need_before:
                        group.insert(min(group.index(need_before.pop()), i), before)
                    else:
                        group.insert(0, before)
                    valid = True
                    for i, page in enumerate(group):
                        for before in must_after[page]:
                            if before not in group[:i] and before in group_set:
                                valid = False
                                break
                        if valid is False:
                            break
                self.debug(group)
                part2 += group[len(group) // 2]
                        

        return part1, part2
