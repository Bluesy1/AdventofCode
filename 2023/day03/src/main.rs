use std::collections::HashMap;

pub fn part1(input: &str) -> String {
    let lines: Vec<Vec<_>> = input.lines()
        .map(str::chars)
        .map(|line| line.collect::<Vec<_>>())
        .collect();
    let mut sum = 0;
    let mut on_number = false;
    lines.iter().enumerate().for_each(|(row_idx, row)| {
        for (col_idx, col) in row.iter().enumerate() {
            if col.is_digit(10) && !on_number {
                on_number = true;
                let number = row[col_idx..].into_iter().take_while(|c| c.is_digit(10)).collect::<String>();
                let mut possible_locs: Vec<char>  = vec![];
                if col_idx > 0 {
                    possible_locs.push(row[col_idx - 1]);
                    if row_idx > 0 {
                        possible_locs.push(lines[row_idx - 1][col_idx - 1]);
                    }
                    if row_idx < lines.len() - 1 {
                        possible_locs.push(lines[row_idx + 1][col_idx - 1]);
                    }
                }
                for i in 0..number.len() + 1 {
                    if col_idx + i >= row.len() {
                        break;
                    }
                    if row_idx > 0 && col_idx + i < row.len() {
                        possible_locs.push(lines[row_idx - 1][col_idx + i]);
                    }
                    if row_idx < lines.len() - 1 && col_idx < lines.len() - 1 {
                        possible_locs.push(lines[row_idx + 1][col_idx + i]);
                    }
                    possible_locs.push(row[col_idx + i]);
                }
                let valid = possible_locs.iter().any(|c| *c != '.' && !c.is_digit(10));
                if valid {
                    sum += number.parse::<u32>().unwrap();
                }
            } else if !col.is_digit(10) {
                on_number = false;
            }
        } 
    });
    sum.to_string()
}

pub fn part2(input: &str) -> String {
    let lines: Vec<Vec<_>> = input.lines()
        .map(str::chars)
        .map(|line| line.collect::<Vec<_>>())
        .collect();
    let mut sum = 0;
    let mut on_number = false;
    let mut gears: HashMap<(usize, usize), Vec<usize>> = HashMap::new();
    lines.iter().enumerate().for_each(|(row_idx, row)| {
        for (col_idx, col) in row.iter().enumerate() {
            if col.is_digit(10) && !on_number {
                on_number = true;
                let number = row[col_idx..].into_iter().take_while(|c| c.is_digit(10)).collect::<String>();
                let mut gear_pos: Vec<(usize, usize)> = vec![];
                if col_idx > 0 {
                    if row[col_idx - 1] == '*' {
                        gear_pos.push((row_idx, col_idx - 1));
                    }
                    if row_idx > 0 && lines[row_idx - 1][col_idx - 1] == '*' {
                        gear_pos.push((row_idx - 1, col_idx - 1));
                    }
                    if row_idx < lines.len() - 1 && lines[row_idx + 1][col_idx - 1] == '*' {
                        gear_pos.push((row_idx + 1, col_idx - 1));
                    }
                }
                for i in 0..number.len() + 1 {
                    if col_idx + i >= row.len() {
                        break;
                    }
                    if row_idx > 0 && lines[row_idx - 1][col_idx + i] == '*' {
                        gear_pos.push((row_idx - 1, col_idx + i));
                    }
                    if row_idx < lines.len() - 1 && lines[row_idx + 1][col_idx + i] == '*' {
                        gear_pos.push((row_idx + 1, col_idx + i));
                    }
                    if row[col_idx + i] == '*' {
                        gear_pos.push((row_idx, col_idx + i));
                    }
                }
                gear_pos.iter().for_each(|pos| {
                    gears.entry(*pos).or_insert(vec![]).push(number.parse().unwrap());
                    if lines[pos.0][pos.1] != '.' && lines[pos.0][pos.1].is_digit(10) {
                        gears.entry((pos.0, pos.1)).or_insert(vec![]).push(number.parse().unwrap());
                    }
                });
            } else if !col.is_digit(10) {
                on_number = false;
            }
        } 
    });
    for gear in gears.values() {
        if gear.len() == 2 {
            sum += gear.iter().product::<usize>();
        }
    }
    sum.to_string()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    println!("Part 1: {}", part1(input));
    println!("Part 2: {}", part2(input));
}