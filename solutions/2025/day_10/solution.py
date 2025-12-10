# puzzle prompt: https://adventofcode.com/2025/day/10

from ...base import StrSplitSolution, answer
from ...utils.tools import *
from collections import deque

from typing import NamedTuple

class Machine(NamedTuple):
    target: list[bool]
    buttons: list[tuple[int, ...]] # each button maps to a tuple of indicator indices it toggles
    joltages: list[int]

import z3

class Solution(StrSplitSolution):
    _year = 2025
    _day = 10

    @answer(449)
    def part_1(self) -> int:
        machines: list[Machine] = []
        for block in self.input:
            sections = block.split(" ")
            target = [s == "#" for s in sections[0][1:-1]]
            buttons = [tuple(nums(s)) for s in sections[1:len(sections)-1]]
            joltages = nums(sections[-1])
            machines.append(Machine(target, buttons, joltages))
        
        accumulator = 0
        # for each machine, find the lowest number of button presses to turn on all indicators
        for machine in machines:
            self.debug(machine)

            initial_state = tuple(False for _ in machine.target)
            queue = deque([(initial_state, 0)])  # (state, presses)
            seen = {initial_state}

            while queue:
                state, presses = queue.popleft()
                if state == tuple(machine.target):
                    accumulator += presses
                    self.debug(f"Machine solved in {presses} presses")
                    break

                for button in machine.buttons:
                    new_state = list(state)
                    for idx in button:
                        new_state[idx] = not new_state[idx]
                    new_state_tuple = tuple(new_state)
                    # print(f"Pressing button toggling indicators {button}, current state {state}, new state {new_state_tuple}, presses {presses}")
                    if new_state_tuple not in seen:
                        seen.add(new_state_tuple)
                        queue.append((new_state_tuple, presses + 1))
                        
            
            if len(queue) == 0:
                self.debug("No solution found for machine")
        
        return accumulator


    @answer(17848)
    def part_2(self) -> int:
        machines: list[Machine] = []
        for block in self.input:
            sections = block.split(" ")
            target = [s == "#" for s in sections[0][1:-1]]
            buttons = [tuple(nums(s)) for s in sections[1:len(sections)-1]]
            joltages = nums(sections[-1])
            machines.append(Machine(target, buttons, joltages))
        
        accumulator = 0
        # for each machine, find the lowest number of button presses to turn on all indicators
        for machine in machines:
            self.debug(machine)
            s = z3.Optimize()
            num_buttons = len(machine.buttons)
            presses = [
                z3.Int(f"press_{i}") for i in range(num_buttons)
            ]
            for i in range(num_buttons):
                s.add(presses[i] >= 0)
            for i in range(len(machine.joltages)):
                s.add(
                    sum(presses[j] for j, button in enumerate(machine.buttons) if i in button) == machine.joltages[i]
                )
            s.minimize(sum(presses))
            assert s.check() == z3.sat
            model = s.model()
            total_presses = sum(model[b].as_long() for b in presses) # pyright: ignore[reportOptionalMemberAccess, reportAttributeAccessIssue]
            self.debug(f"Machine solved in {total_presses} presses")
            accumulator += total_presses
        
        return accumulator

    # @answer((1234, 4567))
    # def solve(self) -> tuple[int, int]:
    #     pass
