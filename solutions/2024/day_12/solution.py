# puzzle prompt: https://adventofcode.com/2024/day/12

from ...base import TextSolution, answer
from ...utils.tools import *


def flood_fill(grid, start):
    region = {start}
    symbol = grid[start]
    queue = [start]
    while queue:
        pos = queue.pop()
        for d in [1, -1, 1j, -1j]:
            new_pos = pos + d
            if new_pos in grid and new_pos not in region and grid[new_pos] == symbol:
                region.add(new_pos)
                queue.append(new_pos)
    return region

def get_area(region):
    return len(region[1])

def get_perimeter(region):
    perimeter = 0
    for pos in region[1]:
        for d in [1, -1, 1j, -1j]:
            new_pos = pos + d
            if new_pos not in region[1]:
                perimeter += 1
    return perimeter

def get_sides_count(region):
    perimeter_objects = set()
    for pos in region[1]:
        for d in [1, -1, 1j, -1j]:
            new_pos = pos + d
            if new_pos not in region[1]:
                perimeter_objects.add((new_pos, d))
    
    distinct_sides = 0
    while len(perimeter_objects) > 0:
        pos, d = perimeter_objects.pop()
        distinct_sides += 1
        nxt = pos + d * 1j
        while (nxt, d) in perimeter_objects:
            perimeter_objects.remove((nxt, d))
            nxt += d * 1j
        nxt = pos + d * -1j
        while (nxt, d) in perimeter_objects:
            perimeter_objects.remove((nxt, d))
            nxt += d * -1j
    return distinct_sides


class Solution(TextSolution):
    _year = 2024
    _day = 12

    @answer((1304764, 811148))
    def solve(self) -> tuple[int, int]:
        grid = {complex(x, y): c for y, row in enumerate(self.input.splitlines()) for x, c in enumerate(row)}

        regions = []

        uncovered = set(grid.keys())
        while len(uncovered) > 0:
            start = uncovered.pop()
            region = flood_fill(grid, start)
            uncovered -= region
            regions.append((grid[start], region))

        parts = [(get_area(region), get_perimeter(region), get_sides_count(region)) for region in regions]

        p1 = sum(area * perimeter for area, perimeter, _ in parts)
        p2 = sum(area * sides_count for area, _, sides_count in parts)

        return p1, p2
