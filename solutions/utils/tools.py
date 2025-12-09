import itertools
import re
from collections import Counter, defaultdict, deque
from functools import cache

__all__ = ("itertools", "re", "Counter", "defaultdict", "deque", "nums", "numsp", "sign", "cache", "Point2D", "Point3D", "Point")

type Point2D = tuple[int, int]
type Point3D = tuple[int, int, int]
type Point = Point2D | Point3D | tuple[int, ...]

def nums(s):
    m = re.findall(r"-?\d+", s)
    return [int(x) for x in m]

numsp = nums

def sign(x):
    if x < 0:
        return -1
    if x == 0:
        return 0
    return 1
