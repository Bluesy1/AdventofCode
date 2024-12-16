# puzzle prompt: https://adventofcode.com/2024/day/16

import heapq
from math import inf

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2024
    _day = 16

    @answer((72428, 456))
    def solve(self) -> tuple[int, int]:
        m = {}
        loc = 0
        exs = 0

        for i, line in enumerate(self.input):
            for j, c in enumerate(line):
                z=i+1j*j
                m[z] = c
                if c == 'S':
                    loc = z
                    m[z] = '.'
                if c == 'E':
                    exs = z
                    m[z] = '.'

        seen = {}
        stack = [(0, 0, str(loc), str(1j))]
        prevs = [(-1, -1)]

        best = inf

        ends = []

        while True:
            cost, ix, loc, d = heapq.heappop(stack)
            loc = complex(loc)
            d = complex(d)

            if cost > best:
                break

            if loc == exs:
                if cost < best:
                    best = cost
                    ends.clear()
                ends.append(ix)
                continue

            if seen.get((loc, d), inf) < cost:
                continue
            seen[(loc, d)] = cost

            if m[loc+d] == '.':

                heapq.heappush(stack, (cost+1, len(prevs), str(loc+d), str(d)))
                prevs.append((ix, loc))  # pyright: ignore[reportArgumentType]

            for d_ in (d*1j, -d*1j):
                heapq.heappush(stack, (cost+1000, len(prevs), str(loc), str(d_)))
                prevs.append((ix, loc))  # pyright: ignore[reportArgumentType]

        seen = {exs}
        for ix in ends:
            loc = exs
            while ix != -1:
                seen.add(loc)
                ix, loc = prevs[ix]  # noqa: PLW2901
        
        return best, len(seen)  # pyright: ignore[reportReturnType]