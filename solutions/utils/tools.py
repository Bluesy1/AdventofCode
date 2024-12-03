import itertools
import re
from collections import Counter, defaultdict, deque

__all__ = ("itertools", "re", "Counter", "defaultdict", "deque", "nums", "numsp", "sign")

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
