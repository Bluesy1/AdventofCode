pub fn part1(input: &str) -> Option<String> {
    input.lines()
        .map(|line| line.chars().filter(|&c| c.is_numeric()))
        .map(|chars| format!("{}{}", chars.clone().next().unwrap(), chars.last().unwrap()))
        .map(|s| s.parse::<i32>().ok().unwrap())
        .sum::<i32>()
        .to_string().into()
}
const STR_DIGITS: &[&[u8]] = &[b"one", b"two", b"three", b"four", b"five", b"six", b"seven", b"eight", b"nine"];

fn digit_sum(w: &[u8]) -> usize {
    let mut digits = (0..w.len()).filter_map(|i| {
    if (b'0'..=b'9').contains(&w[i]) {
        return Some((w[i] - b'0') as usize);
    }
    STR_DIGITS.iter()
        .enumerate()
        .find_map(|(di, d)| w[i..].starts_with(d).then_some(di + 1))
    });
    let a = digits.next().unwrap();
    let b = digits.last().unwrap_or(a);
    a * 10 + b
}

pub fn part2(input: &str) -> i64 {
    input.lines()
        .map(str::as_bytes)
        .map(digit_sum)
        .sum::<usize>() as i64
}

pub fn main() {
    let input = include_str!("../input.txt");
    println!("Part 1: {}", part1(input).unwrap());
    println!("Part 2: {}", part2(input));
}