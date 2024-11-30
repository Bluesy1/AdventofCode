// mod grid;

use std::{vec, collections::HashMap};

pub fn solve(input: &str, expand_by: usize) -> String {
    let grid = input.lines()
    .map(|line| line.chars().map(|char| char == '#').collect::<Vec<_>>())
    .collect::<Vec<_>>();
    // for any row, if it is empty (all elements are false), duplicate it
    let mut expanded_rows: Vec<usize> = vec![];
    grid.iter().enumerate().for_each(|(i, row)| {
        if row.iter().all(|elem| !*elem) {
            expanded_rows.push(i);
        }
    });
    let mut expanded_cols: Vec<bool> = vec![true; grid[0].len()];
    for row in &grid {
        for (i, elem) in row.iter().enumerate() {
            if *elem {
                expanded_cols[i] = false;
            }
        }
    }
    let galaxies = grid.iter().enumerate().flat_map(|(i, row)| {
        row.iter().enumerate().filter_map(|(j, elem)| {
            if *elem {
                Some((i as usize, j as usize))
            } else {
                None
            }
        }).collect::<Vec<_>>()
    });
    let mut distances: HashMap<((usize, usize), (usize, usize)), usize> = HashMap::new();
    for galaxy in galaxies.clone() {
        for other_galaxy in galaxies.clone() {
            if galaxy == other_galaxy {
                continue;
            }
            let entry1 = distances.get_key_value(&(galaxy, other_galaxy));
            let entry2 = distances.get_key_value(&(other_galaxy, galaxy));
            if entry1.is_some() || entry2.is_some() {
                continue;
            }
            let mut dx = (galaxy.0 as isize - other_galaxy.0 as isize).abs() as usize;
            let mut dy = (galaxy.1 as isize - other_galaxy.1 as isize).abs() as usize;
            let min_x = galaxy.0.min(other_galaxy.0) + 1;
            let max_x = galaxy.0.max(other_galaxy.0) - 1;
            if dx != 0{
                for row in min_x..=max_x {
                    if expanded_rows.contains(&row) {
                        dx += expand_by;
                    }
                }
            }
            if dy != 0 {
                let min_y = galaxy.1.min(other_galaxy.1) + 1;
                let max_y = galaxy.1.max(other_galaxy.1) - 1;
                for col in min_y..=max_y {
                // println!("{}", col);
                if expanded_cols[col] {
                    dy += expand_by;
                }
            }
            }
            distances.insert((galaxy, other_galaxy), dx+dy);
        }
    }
    distances.values().sum::<usize>().to_string()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", solve(input, 1));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", solve(input, 999999));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
