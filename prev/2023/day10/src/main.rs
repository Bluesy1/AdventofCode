use std::cmp::*;
use std::collections::*;

#[derive(Debug, Clone, Copy, PartialEq, Default)]
struct Cell {
    north: bool,
    east: bool,
    south: bool,
    west: bool,
    start: bool,
    outside: bool,
}

impl Cell {
    fn from_char(c: char) -> Self {
        match c {
            '|' => Self {
                north: true,
                south: true,
                ..Default::default()
            },
            '-' => Self {
                east: true,
                west: true,
                ..Default::default()
            },
            'L' => Self {
                north: true,
                east: true,
                ..Default::default()
            },
            'J' => Self {
                north: true,
                west: true,
                ..Default::default()
            },
            '7' => Self {
                south: true,
                west: true,
                ..Default::default()
            },
            'F' => Self {
                south: true,
                east: true,
                ..Default::default()
            },
            'O' | 'I' | '.' => Default::default(),
            'S' => Self {
                start: true,
                ..Default::default()
            },
            _ => panic!("{:?}", c),
        }
    }

    fn expand(&self) -> [[Cell; 2]; 2] {
        [
            [
                *self,
                Cell {
                    west: self.east,
                    east: self.east,
                    ..Default::default()
                },
            ],
            [
                Cell {
                    north: self.south,
                    south: self.south,
                    ..Default::default()
                },
                Default::default(),
            ],
        ]
    }
}

pub fn main() {
    let input = include_str!("../input.txt").to_string();
    // let input = include_str!("../test.txt").to_string();
    let time = std::time::Instant::now();
    let mut grid: Vec<Vec<Cell>> = input
        .lines()
        .map(|s| s.chars().map(Cell::from_char).collect())
        .collect();

    let start = (0..grid.len())
        .find_map(|i| (0..grid[i].len()).find_map(|j| (grid[i][j].start == true).then_some((i, j))))
        .unwrap();

    // Calculate initial entries
    grid[start.0][start.1].north = start.0 > 0 && grid[start.0 - 1][start.1].south;
    grid[start.0][start.1].south = start.0 < grid.len() - 1 && grid[start.0 + 1][start.1].north;
    grid[start.0][start.1].west = start.1 > 0 && grid[start.0][start.1 - 1].east;
    grid[start.0][start.1].east =
        start.1 < grid[start.0].len() - 1 && grid[start.0][start.1 + 1].west;

    // Part 1
    let mut distance = 0;
    let mut frontier = BinaryHeap::new();
    let mut visited: HashSet<(usize, usize)> = HashSet::new();
    frontier.push((Reverse(0), start));
    while let Some((Reverse(k), (i, j))) = frontier.pop() {
        if !visited.insert((i, j)) {
            continue;
        }
        distance = distance.max(k);
        let cell = &grid[i][j];
        if cell.north {frontier.push((Reverse(k + 1), (i - 1, j)));}
        if cell.south {frontier.push((Reverse(k + 1), (i + 1, j)));}
        if cell.west {frontier.push((Reverse(k + 1), (i, j - 1)));}
        if cell.east {frontier.push((Reverse(k + 1), (i, j + 1)));}
    }
    println!("{}", distance);
    println!("Part 1 Time: {:?}", time.elapsed());
    let part2_start = std::time::Instant::now();
    // Part 2
    // Clear junk
    for i in 0..grid.len() {
        for j in 0..grid[i].len() {
            if !visited.contains(&(i, j)) {
                grid[i][j] = Default::default();
            }
        }
    }
    // Expand grid so each original cell is 2x2 cells - this provides gaps for flood fill
    let mut expanded: Vec<Vec<Cell>> = grid
        .iter()
        .flat_map(|row| {
            [
                row.iter().flat_map(|cell| cell.expand()[0]).collect(),
                row.iter().flat_map(|cell| cell.expand()[1]).collect(),
            ]
        })
        .collect();

    let mut edges: VecDeque<(usize, usize)> = VecDeque::new();
    let mut excess = 0;
    // Start any cell on the edge of initial grid.
    for i in 0..grid.len() {
        let exp_i = 2 * i;
        edges.push_back((exp_i, 0));
        edges.push_back((exp_i, expanded[exp_i].len() - 1));
    }
    for j in 0..grid[0].len() {
        let exp_j = 2 * j;
        edges.push_back((0, exp_j));
        edges.push_back((expanded.len() - 1, exp_j));
    }

    while let Some((i, j)) = edges.pop_front() {
        if expanded[i][j] != Default::default() {
            // Visited, or part of loop
            continue;
        }
        expanded[i][j].outside = true;
        // Count excess
        if i % 2 == 0 && j % 2 == 0 {
            excess += 1;
        }

        if i > 0 {
            edges.push_back((i - 1, j));
        }
        if i < expanded.len() - 1 {
            edges.push_back((i + 1, j));
        }
        if j > 0 {
            edges.push_back((i, j - 1));
        }
        if j < expanded[i].len() - 1 {
            edges.push_back((i, j + 1));
        }
    }

    let insiders = grid.len() * grid[0].len() - excess - visited.len();

    println!("{insiders}");
    println!("Part 2 Time: {:?}", part2_start.elapsed());
    println!("Total Time: {:?}", time.elapsed());
}
