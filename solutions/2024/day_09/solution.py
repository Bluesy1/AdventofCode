# puzzle prompt: https://adventofcode.com/2024/day/9

from ...base import TextSolution, answer
from ...utils.tools import *


class Solution(TextSolution):
    _year = 2024
    _day = 9

    @answer(6291146824486)
    def part_1(self) -> int:
        file_blocks = []
        file_block = True
        file_num = 0
        for char in self.input.strip():
            num = int(char)
            if file_block:
                file_block = False
                file_blocks.extend([file_num] * num)
            else:
                file_blocks.extend(["."] * num)
                file_block = True
                file_num += 1
        block_len = len(file_blocks)
        idx = 0
        while True:
            try:
                if (idx := file_blocks.index(".", idx)) < block_len:
                    block = file_blocks.pop()
                    if block == ".":
                        continue
                    file_blocks[idx] = block
            except ValueError:
                break
            if "." not in file_blocks:
                break
        return sum(i * val for i, val in enumerate(file_blocks))

    @answer(6307279963620)
    def part_2(self) -> int:
        file_blocks = []
        file_block = True
        file_num = 0
        for char in self.input.strip():
            num = int(char)
            if file_block:
                file_block = False
                file_blocks.extend([file_num] * num)
            else:
                file_blocks.extend(["."] * num)
                file_block = True
                file_num += 1
        for i in range(file_num, 0, -1):
            first_idx = file_blocks.index(i)
            filelen = file_blocks.count(i)
            for j in range(first_idx):
                if file_blocks[j] == ".":
                    idx = j
                    size = len(list(itertools.takewhile(lambda x: file_blocks[x] == ".", range(j, j + filelen))))
                    if size >= filelen:
                        file_blocks[first_idx:first_idx + filelen] = ["."] * filelen
                        file_blocks[idx:idx + filelen] = [i] * filelen
                        break
        
        return sum(i * val for i, val in enumerate(file_blocks) if val != ".")
