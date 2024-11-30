mod grid;

use std::{hash::Hash, fmt::Display};

use grid::Grid;

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
enum Tile {
    Rock,
    Plot(bool),
}

impl From<char> for Tile {
    fn from(c: char) -> Self {
        match c {
            '#' => Tile::Rock,
            '.' => Tile::Plot(false),
            'S' => Tile::Plot(true),
            c => panic!("Invalid tile: {}", c)
        }
    }
}

impl Display for Tile {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Tile::Rock => write!(f, "#"),
            Tile::Plot(false) => write!(f, "."),
            Tile::Plot(true) => write!(f, "O"),
        }
    }
}

impl Grid<Tile> {
    fn step(&mut self) -> Grid<Tile> {
        let mut new_grid = self.clone();
        for ((row, col), tile) in self.iter() {
            if tile != &Tile::Plot(true) {
                continue;
            }
            for (r, c) in vec![(row, col + 1), (row, col - 1), (row + 1, col), (row - 1, col)] {
                if let Some(Tile::Plot(false)) = self.get((r, c)) {
                    new_grid.set((r, c), Tile::Plot(true));
                } 
            }
            new_grid.set((row, col), Tile::Plot(false));
        }
        // self.data = new_grid.data;
        new_grid
    }
}

pub fn part1(input: &str) -> String {
    let mut grid = Grid::from_vec(
        input.lines()
            .map(|line| {line.chars().map(Tile::from).collect()}).collect()
        ).unwrap();
    for _i in 1..=64 {
        grid = grid.step();
        // if i == 6 {
        // println!("After {} steps:", i);
        //     for row in 0..input.lines().next().unwrap().chars().count() {
        //         for col in 0..input.lines().count() {
        //             print!("{}", match grid.get(&(row, col)) {
        //                 Some(Tile::Rock) => '#',
        //                 Some(Tile::Plot(false)) => '.',
        //                 Some(Tile::Plot(true)) => 'O',
        //                 None => panic!("Invalid tile: ({}, {})", row, col)
        //             });
        //         }
        //         println!();
        //     }
        // }
    }
    // println!("{}", grid);
    grid.values().filter(|&&t| t == Tile::Plot(true)).count().to_string()
    // "".into()
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    // I hate maths
    "".into()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    let start_time = std::time::Instant::now();
    println!("Result: {}", part1(input));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", part2(input));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
