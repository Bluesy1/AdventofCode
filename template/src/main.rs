// mod grid;

pub fn part1(input: &str) -> String {
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
