# puzzle prompt: https://adventofcode.com/2025/day/10
import z3

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

            s = z3.Optimize()
            num_buttons = len(buttons)
            presses = [z3.Int(f"press_{i}") for i in range(num_buttons)]
            for i in range(num_buttons):
                s.add(presses[i] >= 0)
            for i in range(len(joltages)):
                s.add(
                    sum(presses[j] for j, button in enumerate(buttons) if i in button)
                    == joltages[i]
                )
            s.minimize(sum(presses))
            assert s.check() == z3.sat
            model = s.model()
            total_presses = sum(model[b].as_long() for b in presses)  # pyright: ignore[reportOptionalMemberAccess, reportAttributeAccessIssue]
            part2 += total_presses

        return part1, part2
