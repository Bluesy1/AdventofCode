// mod grid;

use std::collections::{HashMap, HashSet, VecDeque};

pub fn solve(input: &str) -> () {
    let start_time = std::time::Instant::now();
    let mut brick = Vec::new();
    for line in input.lines() {
        let parts: Vec<&str> = line.split("~").collect();
        let a: Vec<i32> = parts[0].split(",").map(|s| s.parse().unwrap()).collect();
        let b: Vec<i32> = parts[1].split(",").map(|s| s.parse().unwrap()).collect();
        brick.push((a, b));
    }

    let n = brick.len();

    brick.sort_by_key(|x| x.0[2]);

    let mut highest = HashMap::new();
    let mut bad = HashSet::new();
    let mut graph = vec![Vec::new(); n];
    for (idx, b) in brick.iter_mut().enumerate() {
        let mut mxh = -1;
        let mut support_set: HashSet<isize> = HashSet::new();
        for x in b.0[0]..=b.1[0] {
            for y in b.0[1]..=b.1[1] {
                if let Some((h, i)) = highest.get(&(x, y)) {
                    if h + 1 > mxh {
                        mxh = h + 1;
                        support_set = HashSet::new();
                        support_set.insert(*i);
                    } else if h + 1 == mxh {
                        support_set.insert(*i);
                    }
                }
            }
        }

        for &x in &support_set {
            if x != -1 {
                graph[x as usize].push(idx);
            }
        }

        if support_set.len() == 1 {
            bad.insert(support_set.iter().next().unwrap().clone());
        }

        let fall = b.0[2] - mxh;
        if fall > 0 {
            b.0[2] -= fall;
            b.1[2] -= fall;
        }

        for x in b.0[0]..=b.1[0] {
            for y in b.0[1]..=b.1[1] {
                highest.insert((x, y), (b.1[2], idx as isize));
            }
        }
    }

    println!("Part 1: {}", brick.len() - bad.len());
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();

    let mut count = vec![0; n];
    for x in 0..n {
        let mut indeg = vec![0; n];
        for j in 0..n {
            for &i in &graph[j] {
                indeg[i] += 1;
            }
        }
        let mut q = VecDeque::new();
        q.push_back(x);
        count[x] = -1;
        while !q.is_empty() {
            count[x] += 1;
            let x = q.pop_back().unwrap();
            for &i in &graph[x] {
                indeg[i] -= 1;
                if indeg[i] == 0 {
                    q.push_back(i);
                }
            }
        }
    }

    println!("Part 2: {}", count.iter().sum::<i32>());
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}

pub fn main() {
    let input = include_str!("../input.txt");
    let test_input = include_str!("../test.txt");
    println!("========= TEST =========");
    solve(test_input);
    println!("========= REAL =========");
    solve(input);
}
