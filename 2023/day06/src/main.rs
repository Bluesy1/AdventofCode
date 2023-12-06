// mod grid;

pub fn part1(input: &str) -> String {
    let mut lines = input.lines();
    let races = lines.next()
        .unwrap()
        .split_once(": ")
        .unwrap().1
        .trim()
        .split_whitespace()
        .map(|time| time.parse::<usize>().unwrap())
        .collect::<Vec<_>>();
    let to_beat = lines.next()
        .unwrap()
        .split_once(": ")
        .unwrap().1
        .trim()
        .split_whitespace()
        .map(|time| time.parse::<usize>().unwrap())
        .collect::<Vec<_>>();

    let mut res = 1;

    for (length, target) in races.iter().zip(to_beat.iter()) {
        let methods = (0..=*length).map(|time| time * (length - time))
            .filter(|distance| distance > &target)
            .collect::<Vec<_>>()
            .len()
        ;
        res *= methods;
    }

    res.to_string()
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    let mut lines = input.lines();
    let length: usize = lines.next()
        .unwrap()
        .split_once(": ")
        .unwrap().1
        .trim()
        .split_whitespace()
        .collect::<String>()
        .parse()
        .unwrap();
    let target: usize = lines.next()
        .unwrap()
        .split_once(": ")
        .unwrap().1
        .trim()
        .split_whitespace()
        .collect::<String>()
        .parse()
        .unwrap();
    let res: usize = (0..=length).map(|time| time * (length - time))
            .filter(|distance| distance > &target)
            .collect::<Vec<_>>()
            .len();
    res.to_string()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    println!("Part 1: {}", part1(input));
    println!("Part 2: {}", part2(input));
}
