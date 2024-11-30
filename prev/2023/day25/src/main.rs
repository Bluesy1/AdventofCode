// mod grid;

use std::collections::{HashMap, HashSet};

pub fn part1(input: &str) -> String {
    let mut mapping: HashMap<String, HashSet<String>> = HashMap::new();
    for line in input.lines() {
        let (node1, nodes) = line.split_once(":").unwrap();
        let nodes = nodes.trim().split(" ");
        for node in nodes {
            mapping.entry(node1.to_string()).or_insert(HashSet::new()).insert(node.to_string());
            mapping.entry(node.to_string()).or_insert(HashSet::new()).insert(node1.to_string());
        }
    }
    let mut mins = vec![0];
    "".into()
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    "".into()
}

pub fn main() {
    // let input = include_str!("../input.txt");
    let input = include_str!("../test.txt");
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", part1(input));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", part2(input));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
