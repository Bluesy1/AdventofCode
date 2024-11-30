// mod grid;

use std::{fmt::Display, hash::Hash, collections::HashMap};

#[derive(Debug, Clone, PartialEq, Eq)]
struct Grid {
    grid: Vec<Vec<Cell>>,
}

impl From<&str> for Grid {
    fn from(input: &str) -> Self {
        let grid = input
            .lines()
            .map(|line| line.chars().map(Cell::from_char).collect::<Vec<_>>())
            .collect::<Vec<_>>();
        Self { grid }
    }
}

impl ToString for Grid {
    fn to_string(&self) -> String {
        let mut s = String::new();
        for row in &self.grid {
            for cell in row {
                s.push_str(&cell.to_string());
            }
            s.push('\n');
        }
        s
    }
}

impl Hash for Grid {
    fn hash<H: std::hash::Hasher>(&self, state: &mut H) {
        self.to_string().hash(state);
    }
}

impl Grid {
    fn north(&mut self) {
        let mut grid = self.grid.clone();
        for i in 1..grid.len() {
            for j in 0..grid[i].len() {
                if grid[i][j] != Cell::Rolling {
                    continue;
                }
                for k in (0..i).rev() {
                    if grid[k][j] != Cell::Empty {
                        break;
                    }
                    grid[k][j] = Cell::Rolling;
                    grid[k + 1][j] = Cell::Empty;
                }
            }
        }
        self.grid = grid;
    }

    fn south(&mut self) {
        let mut grid = self.grid.clone();
        for i in (0..grid.len() - 1).rev() {
            for j in 0..grid[i].len() {
                if grid[i][j] != Cell::Rolling {
                    continue;
                }
                for k in i + 1..grid.len() {
                    if grid[k][j] != Cell::Empty {
                        break;
                    }
                    grid[k][j] = Cell::Rolling;
                    grid[k - 1][j] = Cell::Empty;
                }
            }
        }
        self.grid = grid;
    }

    fn east(&mut self) {
        let mut grid = self.grid.clone();
        for i in 0..grid.len() {
            for j in (0..grid[i].len() - 1).rev() {
                if grid[i][j] != Cell::Rolling {
                    continue;
                }
                for k in j + 1..grid[i].len() {
                    if grid[i][k] != Cell::Empty {
                        break;
                    }
                    grid[i][k] = Cell::Rolling;
                    grid[i][k - 1] = Cell::Empty;
                }
            }
        }
        self.grid = grid;
    }

    fn west(&mut self) {
        let mut grid = self.grid.clone();
        for i in 0..grid.len() {
            for j in 1..grid[i].len() {
                if grid[i][j] != Cell::Rolling {
                    continue;
                }
                for k in (0..j).rev() {
                    if grid[i][k] != Cell::Empty {
                        break;
                    }
                    grid[i][k] = Cell::Rolling;
                    grid[i][k + 1] = Cell::Empty;
                }
            }
        }
        self.grid = grid;
    }

    fn cycle(&mut self) {
        self.north();
        self.west();
        self.south();
        self.east();
    }

    fn north_weight(&self) -> usize {
        let len = self.grid.len();
        self.grid.iter().enumerate().flat_map(|(i, row)| {
            let value = len - i;
            row.iter().filter_map(|cell| match cell {
                Cell::Rolling => Some(value),
                _ => None,
            }).collect::<Vec<_>>()
        }).sum::<usize>()
    }
}


#[derive(Debug, Clone, Copy, PartialEq, Eq)]
enum Cell {
    Rolling,
    Solid,
    Empty,
}

impl Cell {
    fn from_char(c: char) -> Self {
        match c {
            'O' => Self::Rolling,
            '#' => Self::Solid,
            '.' => Self::Empty,
            _ => panic!("Invalid cell: {}", c),
        }
    }
}

impl Display for Cell {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::Rolling => write!(f, "O"),
            Self::Solid => write!(f, "#"),
            Self::Empty => write!(f, "."),
        }
    }
}

pub fn part1(input: &str) -> String {
    let mut grid = Grid::from(input);
    grid.north();
    grid.north_weight().to_string()
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    let mut grid = Grid::from(input);
    let mut mapping: HashMap<Grid, usize> = HashMap::new();
    let mut i = 0usize;
    let target = 10usize.pow(9);
    while i < target {
        i += 1;
        grid.cycle();
        if let Some(j) = mapping.get(&grid) {
            let cycle_length = i - j;
            let amt = (target - i) / cycle_length;
            i += amt * cycle_length;
        }
        mapping.insert(grid.clone(), i);
    }
    grid.north_weight().to_string()
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
