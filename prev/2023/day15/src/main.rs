// mod grid;

fn hash(seq: &Vec<char>) -> u8 {
    let mut current = 0;
    for code in seq {
        let mut temp: usize = current as usize;
        temp += *code as usize;
        temp *= 17;
        temp %= 256;
        current = temp as u8
    }
    current
}

pub fn part1(input: &str) -> String {
    input
        .split(",")
        .map(|s| s.chars().collect::<Vec<_>>())
        .map(|chars| hash(&chars) as usize)
        .sum::<usize>()
        .to_string()
}

pub fn part2(input: &str) -> String {
    let items = input.split(",").collect::<Vec<_>>();
    let mut lenses = vec![vec![]; 256];
    let mut lens_lengths = vec![std::collections::HashMap::<String, usize>::new(); 256];
    for item in items {
        let label = item.split("=").next().unwrap().split("-").next().unwrap();
        let value = hash(&label.chars().collect::<Vec<_>>());
        if item.contains("-") {
            if lenses[value as usize].contains(&label.to_string()) {
                for i in 0..lenses[value as usize].len() {
                    if lenses[value as usize][i] == label {
                        lenses[value as usize].remove(i);
                        break;
                    }
                }
            }
        }
        if item.contains("=") {
            if !lenses[value as usize].contains(&label.to_string()) {
                lenses[value as usize].push(label.to_string());
            }
            lens_lengths[value as usize].insert(
                label.to_string(),
                item.split("=").last().unwrap().parse::<usize>().unwrap(),
            );
        }
    }
    let mut value = 0;
    for (container, lns) in lenses.iter().enumerate() {
        for (i, lens) in lns.iter().enumerate() {
            value += (container + 1) * (i + 1) * lens_lengths[container][lens];
        }
    }

    value.to_string()
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
