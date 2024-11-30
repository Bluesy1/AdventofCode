// mod grid;

pub fn part1(input: &str) -> String {
    input.lines()
        .map(|line| line.split_once(": ").unwrap().1)
        .map(|line| line.split_once(" | ").unwrap())
        .map(|(winning, drawn)|
            {
                let winning: Vec<_> = winning.split(" ").filter(|c| *c != "").collect();  
                let drawn: Vec<_> = drawn.split(" ").filter(|c| *c != "").collect();
                let mut matches = 0;
                for num in drawn.clone() {
                    if winning.clone().into_iter().any(|w| w == num) {
                        matches += 1;
                    }
                }
                if matches == 0 {
                    0
                } else {
                    let mut value: usize = 1;
                    for _ in 0..matches-1 {
                        value *= 2;
                    }
                    value
                }
            })
        .sum::<usize>()
        .to_string()
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    let cards: Vec<(Vec<&str>, Vec<&str>) > = input.lines()
        .map(|line| line.split_once(": ").unwrap().1)
        .map(|line| line.split_once(" | ").unwrap())
        .map(|(winning, drawn)|
            {
                let winning: Vec<_> = winning.split(" ").filter(|c| *c != "").collect();  
                let drawn: Vec<_> = drawn.split(" ").filter(|c| *c != "").collect();
                (winning, drawn)
            })
        .collect();
    let mut have: Vec<usize> = (0..cards.len()).map(|_| 1).collect();
    // have[0] = 1;
    for i in 0..cards.len() -1 {
        let num_cards = &have[i].clone();
        let (winning, drawn) = &cards[i];
        let mut matches = 0;
        for num in drawn.clone() {
            if winning.clone().into_iter().any(|w| w == num) {
                matches += 1;
            }
        }
        for j in 1..=matches {
            have[i+j] += num_cards;
        }
    }
    have.iter().sum::<usize>().to_string()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    println!("Part 1: {}", part1(input));
    println!("Part 2: {}", part2(input));
}
