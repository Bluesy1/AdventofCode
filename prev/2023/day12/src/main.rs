// mod grid;
use cached::proc_macro::cached;
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
enum Item {
    Operational,
    Broken,
    Unknown,
}

impl From<char> for Item {
    fn from(c: char) -> Self {
        match c {
            '#' => Item::Broken,
            '.' => Item::Operational,
            '?' => Item::Unknown,
            _ => panic!("Invalid item"),
        }
    }
}

#[derive(Debug, Clone)]
struct Row {
    items: Vec<Item>,
    groupings: Vec<usize>,
}

impl Row {
    fn solve(&self) -> usize {
        let mut potential_solutions: Vec<Vec<Item>> = vec![vec![]];
        // let number_unknown = item.items.iter().filter(|i| **i == Item::Unknown).count();
        for i in 0..self.items.len() {
            match self.items[i] {
                Item::Operational => {
                    let temp = potential_solutions.clone();
                    potential_solutions.clear();
                    for mut solution in temp {
                        solution.push(Item::Operational);
                        potential_solutions.push(solution);
                    }
                }
                Item::Broken => {
                    let temp = potential_solutions.clone();
                    potential_solutions.clear();
                    for mut solution in temp {
                        solution.push(Item::Broken);
                        potential_solutions.push(solution);
                    }
                }
                Item::Unknown => {
                    let temp = potential_solutions.clone();
                    potential_solutions.clear();
                    for item in temp {
                        let mut new_solution = item.clone();
                        new_solution.push(Item::Operational);
                        potential_solutions.push(new_solution);
                        let mut new_solution = item.clone();
                        new_solution.push(Item::Broken);
                        potential_solutions.push(new_solution);
                    }
                }
            }
        }
        potential_solutions
            .iter()
            .filter(|solution| {
                let mut groupings_in_sol = vec![];
                let mut current_item = solution[0];
                let mut count = 0;
                for i in 0..solution.len() {
                    if solution[i] == current_item {
                        count += 1;
                    } else {
                        if current_item == Item::Broken {
                            groupings_in_sol.push(count);
                        }
                        count = 1;
                        current_item = solution[i];
                    }
                }
                if current_item == Item::Broken {
                    groupings_in_sol.push(count);
                }
                // println!("{:?}: {:?}", solution, groupings_in_sol);
                groupings_in_sol == self.groupings
            })
            .count()
        // valid_solutions.len()
    }
}

pub fn part1(input: &str) -> String {
    let items: Vec<_> = input
        .lines()
        .map(|line| {
            let (items, groupings) = line.split_once(" ").unwrap();
            let items = items.chars().map(|c| c.into()).collect::<Vec<Item>>();
            let groupings = groupings
                .split(",")
                .map(|s| s.parse::<usize>().unwrap())
                .collect::<Vec<usize>>();
            Row { items, groupings }
        })
        .collect();
    let possibilities = items.iter().map(Row::solve).collect::<Vec<usize>>();
    possibilities.iter().sum::<usize>().to_string()
}

fn parse_input(s: &str) -> impl Iterator<Item = (&str, Vec<usize>)> {
    s.lines().map(|line| {
        let mut parts = line.split_whitespace();
        let a = parts.next().unwrap();
        let b = parts.next().unwrap();
        let b: Vec<usize> = b.split(',').map(|x| x.parse().unwrap()).collect();
        (a, b)
    })
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    let data: Vec<_> = parse_input(input).collect();
    let mut answer = 0;

    fn count_matches(pattern: &str, splits: Vec<usize>) -> usize {
        #[cached]
        fn gen(rem_pattern: String, rem_len: usize, rem_splits: Vec<usize>) -> usize {
            if rem_splits.is_empty() {
                if rem_pattern.chars().all(|char| char == '.' || char == '?') {
                    return 1;
                }
                return 0;
            }
            let a = rem_splits[0];
            let rest = &rem_splits[1..];
            let after: usize = rest.iter().sum::<usize>() + rest.len();
            let mut count = 0;

            for before in 0..=(rem_len - a - after) {
                let cand = ".".repeat(before) + &"#".repeat(a) + ".";
                if rem_pattern
                    .chars()
                    .zip(cand.chars())
                    .all(|(a, b)| a == '?' || a == b)
                {
                    // println!("{} {} {} {}", before, a, cand, rem_pattern);
                    let mut rest_pattern = "".to_string();
                    if cand.len() < rem_pattern.len() {
                        rest_pattern = rem_pattern[cand.len()..].to_string();
                    }
                    count += gen(rest_pattern, rem_len - cand.len(), rest.to_vec());
                }
            }

            count
        }
        gen(pattern.to_string(), pattern.len(), splits)
    }

    for (a, b) in data {
        answer += count_matches(&format!("{}?{}?{}?{}?{}", a, a, a, a, a), b.repeat(5));
    }
    answer.to_string()
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
