# puzzle prompt: https://adventofcode.com/2025/day/10

import numpy as np
from scipy.optimize import linprog 

from ...base import StrSplitSolution, answer
from ...utils.tools import *

type Machine = tuple[list[bool], list[tuple[int, ...]], list[int]]


class Solution(StrSplitSolution):
    _year = 2025
    _day = 10

    @answer((449, 17848))
    def solve(self) -> tuple[int, int]:
        part1 = part2 = 0

        for machine in self.input:
            sections = machine.split(" ")
            target = [s == "#" for s in sections[0][1:-1]]
            buttons = [tuple(nums(s)) for s in sections[1 : len(sections) - 1]]
            joltages = nums(sections[-1])

            # *** Part 1 ***

            initial_state = tuple(False for _ in target)
            queue = deque([(initial_state, 0)])  # (state, presses)
            seen = {initial_state}

            while queue:
                state, presses = queue.popleft()
                if state == tuple(target):
                    part1 += presses
                    break

                for button in buttons:
                    new_state = list(state)
                    for idx in button:
                        new_state[idx] = not new_state[idx]
                    new_state_tuple = tuple(new_state)
                    if new_state_tuple not in seen:
                        seen.add(new_state_tuple)
                        queue.append((new_state_tuple, presses + 1))

            # *** Part 2 ***

            num_buttons = len(buttons)

            A = [
                    [1 if i in button else 0 for button in buttons]
                    for i in range(len(joltages))
                ]
            r = linprog(np.ones(num_buttons), A_eq=A, b_eq=joltages, integrality=1)
            assert r.status == 0
            part2 += int(sum(r.x))

        return part1, part2
