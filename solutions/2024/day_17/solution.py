# puzzle prompt: https://adventofcode.com/2024/day/17

from ...base import TextSolution, answer
from ...utils.tools import *

combo = {0: 0, 1: 1, 2: 2, 3: 3, 4: "A", 5: "B", 6: "C", 7: None}

def combo_op(A: int, B: int, C: int, operand: int) -> int:
    match operand:
        case 0 | 1 | 2 | 3:
            return operand
        case 4:
            return A
        case 5:
            return B
        case 6:
            return C

def adv(A: int, B: int, C: int, operand: int, pos: int) -> tuple[int, int, int]:
    # print(f"adv: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return A // (2**combo_op(A, B, C, operand)), B, C

def bxl(A: int, B: int, C: int, operand: int, pos: int) -> tuple[int, int, int]:
    # print(f"bxl: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return A, B ^ operand, C

def bst(A: int, B: int, C: int, operand: int, pos: int) -> tuple[int, int, int]:
    # print(f"bst: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return A, combo_op(A, B, C, operand) % 8, C

def jnz(A: int, B: int, C: int, operand: int, pos: int) -> int:
    # print(f"jnz: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return operand if A != 0 else pos + 2

def bxc(A: int, B: int, C: int, operand: int, pos: int) -> tuple[int, int, int]:
    # print(f"bxc: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return A, B ^ C, C

def out(A: int, B: int, C: int, operand: int, pos: int) -> str:
    # print(f"out: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return f"{combo_op(A, B, C, operand)%8}"

def bdv(A: int, B: int, C: int, operand: int, pos: int) -> tuple[int, int, int]:
    # print(f"bdv: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return A, A // (2**combo_op(A, B, C, operand)), C

def cdv(A: int, B: int, C: int, operand: int, pos: int) -> tuple[int, int, int]:
    # print(f"cdv: {A=}, {B=}, {C=}, {operand=}, {pos=}")
    return A, B, A // (2**combo_op(A, B, C, operand))

opcodes = {
    0: adv,
    1: bxl,
    2: bst,
    3: jnz,
    4: bxc,
    5: out,
    6: bdv,
    7: cdv
}

class Solution(TextSolution):
    _year = 2024
    _day = 17

    @answer("4,6,1,4,2,1,3,1,6")
    def part_1(self) -> str:
        registers, prog = self.input.split("\n\n")
        A,B,C = nums(registers)
        prog = nums(prog)
        # print(prog)
        pointer = 0
        output = []
        while pointer < len(prog):
            opcode = opcodes[prog[pointer]]
            operand = prog[pointer + 1]
            ret = opcode(A, B, C, operand, pointer)
            if isinstance(ret, int):
                pointer = ret
                continue
            if isinstance(ret, tuple):
                A, B, C = ret
            elif isinstance(ret, str):
                output.append(ret)
            else:
                raise ValueError("Invalid return type")
            pointer += 2
        return ",".join(output)


    # @answer(1234)
    def part_2(self) -> int:
        pass

    # @answer((1234, 4567))
    # def solve(self) -> tuple[int, int]:
    #     pass

# 7,6,5,6,0,1,7,1,6