# puzzle prompt: https://adventofcode.com/2024/day/14

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Robot:
    def __init__(self, row, size):
        m = re.search("p=(?P<x>[0-9-]+),(?P<y>[0-9-]+) v=(?P<vx>[0-9-]+),(?P<vy>[0-9-]+)", row)
        self.x = int(m["x"])
        self.y = int(m["y"])
        self.vx = int(m["vx"])
        self.vy = int(m["vy"])

def calc(values, mode, size=(101, 103)):

    robots = [Robot(row, size) for row in values]

    for y in range(size[1]):
        row = ""
        for x in range(size[0]):
            z = 0
            for cur in robots:
                if cur.x == x and cur.y == y:
                    z += 1
            row += "." if z == 0 else str(z)

    if mode == 2:
        i = 0
        while True:
            i += 1
            seen = set()
            for cur in robots:
                cur.x = (cur.x + cur.vx) % size[0]
                cur.y = (cur.y + cur.vy) % size[1]
                seen.add((cur.x, cur.y))
            if len(seen) == len(robots):
                
                # print the grid
                # for y in range(size[1]):
                #     row = ""
                #     for x in range(size[0]):
                #         z = 0
                #         for cur in robots:
                #             if cur.x == x and cur.y == y:
                #                 z += 1
                #         row += "." if z == 0 else str(z)
                #     print(row)
                
                return i


    for _ in range(100):
        for cur in robots:
            cur.x = (cur.x + cur.vx) % size[0]
            cur.y = (cur.y + cur.vy) % size[1]

    ret = 1
    for x in [0, size[0] // 2 + 1]:
        for y in [0, size[1] // 2 + 1]:
            count = 0
            for cur in robots:
                if cur.x >= x and cur.x < x + size[0] // 2 and cur.y >= y and cur.y < y + size[1] // 2:
                    count += 1
            ret *= count

    return ret

class Solution(StrSplitSolution):
    _year = 2024
    _day = 14

    @answer((217328832, 7412))
    def solve(self) -> tuple[int, int]:
        return calc(self.input, 1), calc(self.input, 2)
