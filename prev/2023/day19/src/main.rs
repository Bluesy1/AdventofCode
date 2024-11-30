// mod grid;

use std::collections::HashMap;

#[derive(Debug, Clone, PartialEq, Eq)]
struct Condition {
    checking: char,
    less_than: bool,
    value: usize,
    if_true: String,
}

impl Condition {
    fn new(checking: char, less_than: bool, value: usize, if_true: String) -> Self {
        match checking {
            'x' => (),
            'm' => (),
            'a' => (),
            's' => (),
            c => panic!("Invalid check: {}", c),
        }
        Self {
            checking,
            less_than,
            value,
            if_true,
        }
    }

    fn evaluate(&self, target: &Part) -> bool {
        let against = match self.checking {
            'x' => target.x,
            'm' => target.m,
            'a' => target.a,
            's' => target.s,
            c => panic!("Invalid check: {}", c),
        };
        if self.less_than {
            against < self.value
        } else {
            against > self.value
        }
    }
}

#[derive(Debug, Clone, PartialEq, Eq)]
struct Workflow {
    conditions: Vec<Condition>,
    fallback: String,
}

impl Workflow {
    fn new(conditions: Vec<Condition>, fallback: String) -> Self {
        Self {
            conditions,
            fallback,
        }
    }

    fn evaluate(&self, target: &Part) -> String {
        for condition in &self.conditions {
            if condition.evaluate(target) {
                return condition.if_true.clone();
            }
        }
        self.fallback.clone()
    }
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
struct Part {
    x: usize,
    m: usize,
    a: usize,
    s: usize,
}

impl Part {
    fn sum(&self) -> usize {
        self.x + self.m + self.a + self.s
    }
}

pub fn part1(input: &str) -> String {
    let mut workflows: HashMap<String, Workflow> = HashMap::new();
    let mut parts: Vec<Part> = Vec::new();
    let mut lines = input.lines();
    let mut line = lines.next().unwrap();
    let mut accepted = Vec::new();
    while line != "" {
        // px{a<2006:qkq,m>2090:A,rfg}
        let (part_name, workflow) = line.split_once("{").unwrap();
        let workflow = workflow.trim_end_matches("}");
        let steps: Vec<&str> = workflow.split(",").collect();
        let mut conditions = Vec::new();
        let mut fallback = String::new();
        for step in steps {
            if step.contains(":") {
                let (condition, if_true) = step.split_once(":").unwrap();
                if condition.contains(">") {
                    let (checking, value) = condition.split_once(">").unwrap();
                    let checking = checking.chars().next().unwrap();
                    let value = value.parse().unwrap();
                    let less_than = false;
                    conditions.push(Condition::new(checking, less_than, value, if_true.to_string()));
                } else if condition.contains("<") {
                    let (checking, value) = condition.split_once("<").unwrap();
                    let checking = checking.chars().next().unwrap();
                    let value = value.parse().unwrap();
                    let less_than = true;
                    conditions.push(Condition::new(checking, less_than, value, if_true.to_string()));
                } else {
                    panic!("Invalid condition: {}", condition);
                }
            } else {
                fallback = step.to_string();
            }
        }
        workflows.insert(part_name.to_string(), Workflow::new(conditions, fallback));
        line = lines.next().unwrap();
    }
    // line = lines.next().unwrap();
    for line in lines {
        // println!("{}", line);
        let values = line.trim_start_matches("{").trim_end_matches("}");
        let values: Vec<&str> = values.split(",").collect();
        let x = values[0].split_once("=").unwrap().1.parse().unwrap();
        let m = values[1].split_once("=").unwrap().1.parse().unwrap();
        let a = values[2].split_once("=").unwrap().1.parse().unwrap();
        let s = values[3].split_once("=").unwrap().1.parse().unwrap();
        parts.push(Part { x, m, a, s });
    }
    // println!("{:?}", workflows);
    // println!("{:?}", parts);
    let initial = workflows.get("in").unwrap().clone();
    let end_states = vec!["A", "R"];
    for part in parts {
        let mut status = initial.evaluate(&part);
        while !end_states.contains(&status.as_str()) {
            let workflow = workflows.get(&status).unwrap();
            status = workflow.evaluate(&part);
        }
        if status == "A" {
            accepted.push(part);
        }
    }
    // println!("{:?}", accepted);
    accepted.iter().map(Part::sum).sum::<usize>().to_string()
    // "".into()
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
struct Bounds {
    x: (i32, i32),
    m: (i32, i32),
    a: (i32, i32),
    s: (i32, i32),
}

impl Default for Bounds {
    fn default() -> Self {
        Self {
            x: (1, 4000),
            m: (1, 4000),
            a: (1, 4000),
            s: (1, 4000),
        }
    }
}

impl Bounds {
    fn get(&self, ch: char) -> (i32, i32) {
        match ch {
            'x' => self.x,
            'm' => self.m,
            'a' => self.a,
            's' => self.s,
            c => panic!("Invalid check: {}", c),
        }
    }

    fn set(&mut self, ch: char, val: (i32, i32)) {
        match ch {
            'x' => self.x = val,
            'm' => self.m = val,
            'a' => self.a = val,
            's' => self.s = val,
            c => panic!("Invalid check: {}", c),
        }
    }

    fn value(&self) -> usize {
        (self.x.1 - self.x.0 +1 ) as usize * (self.m.1 - self.m.0 +1 ) as usize * (self.a.1 - self.a.0 +1 ) as usize * (self.s.1 - self.s.0 +1 ) as usize
    }
}

fn both(ch: char, gt: bool, val: i32, ranges: &mut Vec<Bounds>) -> Vec<Bounds> {
    // let ch = "xmas".chars().position(|c| c == ch).unwrap();
    let mut ranges2 = Vec::new();
    for rng in ranges.iter_mut() {
        let (lo, hi) = rng.get(ch);
        let (lo, hi) = if gt {
            (std::cmp::max(lo, val + 1), hi)
        } else {
            (lo, std::cmp::min(hi, val - 1))
        };
        if lo > hi {
            continue;
        }
        rng.set(ch, (lo, hi));
        ranges2.push(*rng);
    }
    ranges2
}

fn acceptance_ranges_outer(work: &str, workflow: &HashMap<String, String>) -> Vec<Bounds> {
    acceptance_ranges_inner(&workflow[work].split(",").collect::<Vec<&str>>(), workflow)
}

fn acceptance_ranges_inner(w: &Vec<&str>, workflow: &HashMap<String, String>) -> Vec<Bounds> {
    let it = w[0];
    match it {
        "R" => vec![],
        "A" => vec![Bounds::default()],
        _ => {
            if !it.contains(":") {
                return acceptance_ranges_outer(it, workflow);
            }
            let split: Vec<&str> = it.split(":").collect();
            let cond = split[0];
            let gt = cond.contains(">");
            let ch = cond.chars().nth(0).unwrap();
            let val: i32 = cond[2..].parse().unwrap();
            let val_inverted = if gt { val + 1 } else { val - 1 };
            let if_cond_is_true = both(ch, gt, val, &mut acceptance_ranges_inner(&mut vec![split[1]], workflow));
            let if_cond_is_false = both(ch, !gt, val_inverted, &mut acceptance_ranges_inner(&mut w[1..].to_vec(), workflow));
            [if_cond_is_true, if_cond_is_false].concat()
        }
    }
}

pub fn part2(input: &str) -> String {
    let (workflow, _) = input.split_once("\n\n").unwrap();
    let workflow: HashMap<String, String> = workflow
        .lines()
        .map(|line| {
            let (name, workflow) = line.split_once("{").unwrap();
            (name.to_string(), workflow.trim_end_matches("}").to_string())
        })
        .collect();
    acceptance_ranges_outer(&"in", &workflow).iter().map(Bounds::value).sum::<usize>().to_string()
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
