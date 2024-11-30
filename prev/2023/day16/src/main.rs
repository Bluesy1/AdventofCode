// mod grid;

use std::collections::{HashSet, VecDeque};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
enum Facing {
    Up,
    Down,
    Left,
    Right
}

impl From<char> for Facing {
    fn from(c: char) -> Self {
        match c {
            '^' => Self::Up,
            'v' => Self::Down,
            '<' => Self::Left,
            '>' => Self::Right,
            _ => panic!("{:?}", c),
        }
    }
}

impl Facing {
    fn direction(&self) -> (i32, i32) {
        match self {
            Self::Up => (-1, 0),
            Self::Down => (1, 0),
            Self::Left => (0, -1),
            Self::Right => (0, 1),
        }
    }
}

fn energize(start: ((isize, isize), Facing), grid: &Vec<Vec<char>>) -> usize {
    let mut e: HashSet<(isize, isize)> = HashSet::new();
    let mut v: HashSet<((isize, isize), Facing)> = HashSet::new();
    let mut q: VecDeque<((isize, isize), Facing)> = VecDeque::new();
    q.push_back(start);
    while !q.is_empty() {
        let c = q.pop_front().unwrap();
        if v.contains(&c) {
            continue;
        }
        e.insert(c.0);
        let d = c.1;
        v.insert(c);
        let n = (c.0 .0 as i32 + d.direction().0, c.0 .1 as i32 + d.direction().1);
        if n.0 < 0 || n.0 >= grid.len() as i32 || n.1 < 0 || n.1 >= grid[0].len() as i32 {
            continue;
        }
        let cc = grid[n.0 as usize][n.1 as usize];
        if cc == '\\' {
            if d == Facing::Right || d == Facing::Up {
                q.push_back(((n.0 as isize, n.1 as isize), if d == Facing::Right { Facing::Down } else { Facing::Left }));
            } else if d == Facing::Left || d == Facing::Down {
                q.push_back(((n.0 as isize, n.1 as isize), if d == Facing::Left { Facing::Up } else { Facing::Right }));
            }
        } else if cc == '/' {
            if d == Facing::Right || d == Facing::Down {
                q.push_back(((n.0 as isize, n.1 as isize), if d == Facing::Right { Facing::Up } else { Facing::Left }));
            } else if d == Facing::Left || d == Facing::Up {
                q.push_back(((n.0 as isize, n.1 as isize), if d == Facing::Left { Facing::Down } else { Facing::Right }));
            }
        } else if cc == '|' && (d == Facing::Left || d == Facing::Right) {
            q.push_back(((n.0 as isize, n.1 as isize), Facing::Up));
            q.push_back(((n.0 as isize, n.1 as isize), Facing::Down));
        } else if cc == '-' && (d == Facing::Up || d == Facing::Down) {
            q.push_back(((n.0 as isize, n.1 as isize), Facing::Left));
            q.push_back(((n.0 as isize, n.1 as isize), Facing::Right));
        } else {
            q.push_back(((n.0 as isize, n.1 as isize), d));
        }
    }
    e.len() - 1
}

pub fn part1(input: &str) -> String {
    let grid: Vec<Vec<char>> = input.lines().map(|l| l.chars().collect()).collect();
    energize(((0, -1), Facing::Right), &grid).to_string()
}

pub fn part2(input: &str) -> String {
    let grid: Vec<Vec<char>> = input.lines().map(|l| l.chars().collect()).collect();
    let mut m = 0;
    for i in 0..grid.len() {
        m = m.max(energize(((i as isize, -1), Facing::Right), &grid));
        m = m.max(energize(((i as isize, grid[0].len() as isize), Facing::Left), &grid));
    }
    for i in 0..grid[0].len() {
        m = m.max(energize(((-1, i as isize), Facing::Down), &grid));
        m = m.max(energize(((grid.len() as isize, i as isize), Facing::Up), &grid));
    }
    m.to_string()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", part1(input));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", part2(input));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
