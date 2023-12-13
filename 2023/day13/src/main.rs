// mod grid;

fn parse_grid(input_str: &str) -> Vec<Vec<char>> {
    input_str
        .lines()
        .collect::<Vec<_>>()
        .iter()
        .map(|line| line.trim())
        .filter(|line| !line.is_empty())
        .map(|line| line.chars().collect())
        .collect()
}

fn get_row(grid: &Vec<Vec<char>>, row: i64) -> String {
    grid[row as usize].iter().collect()
}

fn get_col(grid: &Vec<Vec<char>>, col: i64) -> String {
    grid.iter().map(|row| row[col as usize]).collect()
}

fn find_rflctn(grid: Vec<Vec<char>>) -> (usize, usize) {
    for col in 1..grid[0].len() {
        let mut l = col as i64 - 1;
        let mut r = col;
        let mut valid = true;
        while l >= 0 && r < grid[0].len() {
            if get_col(&grid, l) != get_col(&grid, r as i64) {
                valid = false;
                break;
            }
            l -= 1;
            r += 1;
        }
        if valid {
            return (0, col);
        }
    }
    for row in 1..grid.len() {
        let mut u = row as i64 - 1;
        let mut d = row;
        let mut valid = true;
        while u >= 0 && d < grid.len() {
            if get_row(&grid, u) != get_row(&grid, d as i64) {
                valid = false;
                break;
            }
            u -= 1;
            d += 1;
        }
        if valid {
            return (row, 0);
        }
    }
    return (0, 0);
}

fn cmp_rows(grid: &Vec<Vec<char>>, row1: i64, row2: i64) -> i64 {
    get_row(grid, row1)
        .chars()
        .zip(get_row(grid, row2).chars())
        .filter(|&(c1, c2)| c1 != c2)
        .count() as i64
}

fn cmp_cols(grid: &Vec<Vec<char>>, col1: i64, col2: i64) -> i64 {
    get_col(grid, col1)
        .chars()
        .zip(get_col(grid, col2).chars())
        .filter(|&(c1, c2)| c1 != c2)
        .count() as i64
}

fn find_rflctn2(grid: Vec<Vec<char>>) -> (usize, usize) {
    for col in 1..grid[0].len() {
        let mut l = col as i64 - 1;
        let mut r = col;
        let mut diffs = 0;
        while l >= 0 && r < grid[0].len() {
            if diffs > 1 {
                break;
            }
            diffs += cmp_cols(&grid, l, r as i64);
            l -= 1;
            r += 1;
        }
        if diffs == 1 {
            return (0, col);
        }
    }
    for row in 1..grid.len() {
        let mut u = row as i64 - 1;
        let mut d = row;
        let mut diffs = 0;
        while u >= 0 && d < grid.len() {
            if diffs > 1 {
                break;
            }
            diffs += cmp_rows(&grid, u, d as i64);
            u -= 1;
            d += 1;
        }
        if diffs == 1 {
            return (row, 0);
        }
    }
    return (0, 0);
}

pub fn part1(input: &Vec<String>) -> i64  {
    let mut total: i64 = 0;
    for i in input {
        let grid = parse_grid(i.as_str());
        let (h, v) = find_rflctn(grid);
        total += 100 * h as i64;
        total += v as i64;
    }
    total
}

pub fn part2(input: &Vec<String>) -> i64 {
    let mut total: i64 = 0;
    for i in input {
        let grid = parse_grid(i.as_str());
        let (h, v) = find_rflctn2(grid);
        total += 100 * h as i64;
        total += v as i64;
    }
    total
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    let mut grids = Vec::new();
    let mut grid = Vec::new();
    for line in input.lines() {
        if line == "" {
            grids.push(grid.join("\n"));
            grid = Vec::new();
        } else {
            grid.push(line.to_string());
        }
    }
    grids.push(grid.join("\n")); // Convert &str to String
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", part1(&grids));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", part2(&grids));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
