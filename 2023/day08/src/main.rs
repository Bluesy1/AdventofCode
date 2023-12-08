use std::collections::HashMap;

// mod grid;

fn steps(inital: &str, inst: &Vec<char>, words: &HashMap<String, String>) -> usize {
    let mut sum = 0;
    let mut pos = inital.to_string();
    loop {
        if pos.ends_with("Z") {
            break;
        }
        let i = inst[sum % inst.len()];
        let n = words[&pos].clone();
        let n = n.replace("(", "").replace(")", "");
        let (l, r) = n.split_once(", ").unwrap().to_owned();
        let l = l.trim().to_string();
        let r = r.trim().to_string();
        if i == 'L' {
            pos = l;
        } else {
            pos = r;
        }
        sum += 1;
    }

    return sum;
}

pub fn lcm(nums: &[usize]) -> usize {
    if nums.len() == 1 {
        return nums[0];
    }
    let a = nums[0];
    let b = lcm(&nums[1..]);
    a * b / gcd_of_two_numbers(a, b)
}

fn gcd_of_two_numbers(a: usize, b: usize) -> usize {
    if b == 0 {
        return a;
    }
    gcd_of_two_numbers(b, a % b)
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test1.txt");
    // let input = include_str!("../test2.txt");

    let mut lines = input.lines();
    let instructions: Vec<char> = lines.next().unwrap().chars().collect();
    lines.next();
    let mut words: HashMap<String, String> = HashMap::new();
    for line in lines {
        let (a, b) = line.split_once(" = ").unwrap();
        words.insert(a.to_string(), b.to_string());
    }

    println!("Part 1: {}", steps("AAA", &instructions, &words));

    let values = words.keys().filter_map(|k| {
        if k.ends_with("A") {
            Some(steps(k, &instructions, &words))
        } else {
            None
        }
    });


    println!("Part 2: {}", lcm(&values.collect::<Vec<usize>>()));
}
