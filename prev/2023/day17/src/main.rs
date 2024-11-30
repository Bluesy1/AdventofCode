// mod grid;

use std::collections::{BinaryHeap, HashMap};

fn dijkstra(grid: &[&[u8]], min_step: isize, max_step: isize) -> i64 {
    let mut distances = HashMap::new();
    let mut queue = BinaryHeap::from_iter([(0, (0, 0, (0, 0)))]);
    while let Some((cost, (row, col, d))) = queue.pop() {
        if (row, col) == (grid.len() - 1, grid[0].len() - 1) {
            return -cost;
        }
        if distances.get(&(row, col, d)).is_some_and(|&c| -cost > c) {
            continue;
        }
        for (dr, dc) in [(-1, 0), (1, 0), (0, -1), (0, 1)] {
            if d == (dr, dc) || d == (-dr, -dc) {
                continue;
            }
            let mut next_cost = -cost;
            for dist in 1..=max_step {
                let rr = (row as isize + dr * dist) as usize;
                let cc = (col as isize + dc * dist) as usize;
                if rr >= grid.len() || cc >= grid[0].len() {
                    continue;
                }
                next_cost += (grid[rr][cc] - b'0') as i64;
                let key = (rr, cc, (dr, dc));
                if min_step <= dist && next_cost < *distances.get(&key).unwrap_or(&10000000) {
                    distances.insert(key, next_cost);
                    queue.push((-next_cost, key));
                }
            }
        }
    }
    unreachable!()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    let grid = input.split('\n').map(str::as_bytes).collect::<Vec<_>>();
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", dijkstra(&grid, 1, 3));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", dijkstra(&grid, 4, 10));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
