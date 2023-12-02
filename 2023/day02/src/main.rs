pub fn part1(input: &str) -> String {
    let mut sum = 0;
    for (idx, line) in input.lines().enumerate() {
        let game = idx + 1;
        let draws = line.trim().split(&":")
            .nth(1)
            .unwrap()
            .trim()
            .split(";")
            .collect::<Vec<_>>();
        let mut red = 0;
        let mut green = 0;
        let mut blue = 0;
        for draw in draws {
            draw.split(",")
                .map(str::trim)
                .map(|item| item.split(" ").take(2))
                .map(|mut item| (item.clone().nth(1).unwrap(), item.nth(0).unwrap().parse::<usize>().unwrap()))
                .for_each(|(item, amt)| {
                    if item == "red" {
                        red = if red >= amt {red} else {amt};
                    } else if item == "green" {
                        green = if green >= amt {green} else {amt};
                    } else if item == "blue" {
                        blue = if blue >= amt {blue} else {amt};
                    } else {
                        panic!("Unknown color: {}", item);
                    }
                });
        }
        if red <= 12 && green <= 13 && blue <= 14 {
            sum += game;
        }
    }
    sum.to_string()
}

pub fn part2(input: &str) -> String {
    let mut sum = 0;
    for line in input.lines() {
        let draws = line.trim().split(&":")
            .nth(1)
            .unwrap()
            .trim()
            .split(";")
            .collect::<Vec<_>>();
        let mut red = 0;
        let mut green = 0;
        let mut blue = 0;
        for draw in draws {
            draw.split(",")
                .map(str::trim)
                .map(|item| item.split(" ").take(2))
                .map(|mut item| (item.clone().nth(1).unwrap(), item.nth(0).unwrap().parse::<usize>().unwrap()))
                .for_each(|(item, amt)| {
                    if item == "red" {
                        red = if red >= amt {red} else {amt};
                    } else if item == "green" {
                        green = if green >= amt {green} else {amt};
                    } else if item == "blue" {
                        blue = if blue >= amt {blue} else {amt};
                    } else {
                        panic!("Unknown color: {}", item);
                    }
                });
        }
        sum += red * green * blue;
    }
    sum.to_string()
}

pub fn main() {
    let input = include_str!("../input.txt");
    println!("Part 1: {}", part1(input));
    println!("Part 2: {}", part2(input));
}