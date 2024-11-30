// mod grid;

use std::collections::{HashMap, VecDeque, HashSet};

pub fn lcm(nums: &[i128]) -> i128 {
    if nums.len() == 1 {
        return nums[0];
    }
    let a = nums[0];
    let b = lcm(&nums[1..]);
    a * b / gcd_of_two_numbers(a, b)
}

fn gcd_of_two_numbers(a: i128, b: i128) -> i128 {
    if b == 0 {
        return a;
    }
    gcd_of_two_numbers(b, a % b)
}

fn adjust(y: &str, typ: &HashMap<String, String>) -> String {
    match typ.get(y) {
        Some(t) => format!("{}{}", t, y),
        None => y.to_string(),
    }
}
pub fn solve(input: &str, part2: bool) -> String {

    let l: Vec<&str> = input.split('\n').collect();

    let mut typ: HashMap<String, String> = HashMap::new();

    let mut r: HashMap<String, Vec<String>> = HashMap::new();
    for line in l {
        let parts: Vec<&str> = line.split("->").map(|s| s.trim()).collect();
        let (src, dest) = (parts[0], parts[1]);
        let dest: Vec<String> = dest.split(", ").map(|s| s.to_string()).collect();
        r.insert(src.to_string(), dest);
        typ.insert(src[1..].to_string(), src.chars().next().unwrap().to_string());
    }

    let mut from: HashMap<String, HashMap<String, String>> = HashMap::new();
    let mut inv: HashMap<String, Vec<String>> = HashMap::new();
    for (x, ys) in r.clone().iter() {
        r.insert(x.to_string(), ys.iter().map(|y| adjust(y, &typ)).collect::<Vec<String>>());
        for y in r.get(x).unwrap() {
            if y.chars().next().unwrap() == '&' {
                if !from.contains_key(y) {
                    from.insert(y.to_string(), HashMap::new());
                }
                from.get_mut(y).unwrap().insert(x.to_string(), "lo".to_string());
            }
            inv.entry(y.to_string()).or_insert(Vec::new()).push(x.to_string());
        }
    }

    assert_eq!(inv.get("rx").unwrap().len(), 1);
    assert_eq!(inv.get("rx").unwrap()[0].chars().next().unwrap(), '&');
    let watch = inv.get(&inv.get("rx").unwrap()[0]).unwrap();

    let (mut lo, mut hi) = (0, 0);
    let mut q: VecDeque<(String, String, String)> = VecDeque::new();
    let mut on: HashSet<String> = HashSet::new();
    let mut prev: HashMap<String, i128> = HashMap::new();
    let mut count: HashMap<String, i128> = HashMap::new();
    let mut to_lcm: Vec<i128> = Vec::new();
    for t in 1..100_000_000 {
        q.push_back(("broadcaster".to_string(), "button".to_string(), "lo".to_string()));

        while let Some((x, from_, typ)) = q.pop_front() {
            if typ == "lo" {
                if prev.contains_key(&x) && count[&x] == 2 && watch.contains(&x) {
                    to_lcm.push(t - prev[&x]);
                }
                *prev.entry(x.clone()).or_insert(t) = t;
                *count.entry(x.clone()).or_insert(0) += 1;
            }
            if to_lcm.len() == watch.len() && part2 {
                return lcm(&to_lcm).to_string();
            }
        
            if x == "rx" && typ == "lo" && part2 {
                return (t+1).to_string();
            }
        
            if typ == "lo" {
                lo += 1;
            } else {
                hi += 1;
            }
        
            if !r.contains_key(&x) {
                continue;
            }
            if x == "broadcaster" {
                for y in &r[&x] {
                    q.push_back((y.clone(), x.clone(), typ.clone()));
                }
            } else if x.chars().next().unwrap() == '%' {
                if typ == "hi" {
                    continue;
                } else {
                    let new_typ = if !on.contains(&x) {
                        on.insert(x.clone());
                        "hi"
                    } else {
                        on.remove(&x);
                        "lo"
                    };
                    for y in &r[&x] {
                        q.push_back((y.clone(), x.clone(), new_typ.to_string()));
                    }
                }
            } else if x.chars().next().unwrap() == '&' {
                from.get_mut(&x).unwrap().insert(from_.clone(), typ.clone());
                let new_typ = if from[&x].values().all(|y| y == "hi") {
                    "lo"
                } else {
                    "hi"
                };
                for y in &r[&x] {
                    q.push_back((y.clone(), x.clone(), new_typ.to_string()));
                }
            } else {
                panic!("Invalid value: {}", x);
            }
        }
        if t == 1000  && !part2{
            return (lo * hi).to_string();
        }
    }
    unreachable!()
}

pub fn part1(input: &str) -> String {
    solve(input, false)
}

pub fn part2(input: &str) -> String {
    solve(input, true)
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test1.txt");
    // let input = include_str!("../test2.txt");
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", part1(input));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", part2(input));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
