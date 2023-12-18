// mod grid;

fn poly_area(poly: &[(i32, i32)]) -> f64 {
    let mut area = 0.0;
    for i in 0..poly.len() {
        let j = (i + 1) % poly.len();
        area += poly[i].0 as f64 * poly[j].1 as f64;
        area -= poly[j].0 as f64 * poly[i].1 as f64;
    }
    area.abs() / 2.0
}

fn calc(values: &Vec<&str>, mode: i32) -> i64 {
    let mut lines = vec![(0, 0)];
    let (mut x, mut y) = (0, 0);
    let mut line_len = 0;
    for row in values {
        let row = row.split_whitespace().collect::<Vec<&str>>();
        let (ox, oy, l) = if mode == 1 {
            let (ox, oy) = match row[0] {
                "R" => (1, 0),
                "D" => (0, 1),
                "L" => (-1, 0),
                "U" => (0, -1),
                _ => panic!("Invalid direction"),
            };
            let l = row[1].parse::<i32>().unwrap();
            (ox, oy, l)
        } else {
            let (ox, oy) = match row[2][row[2].len()-2..].trim_end_matches(")").trim_start_matches("(") {
                "0" => (1, 0),
                "1" => (0, 1),
                "2" => (-1, 0),
                "3" => (0, -1),
                c => panic!("Invalid direction: {}", c),
            };
            let l = i32::from_str_radix(&row[2][2..7], 16).unwrap();
            (ox, oy, l)
        };
        x += ox * l;
        y += oy * l;
        lines.push((x, y));
        line_len += l;
    }
    (poly_area(&lines) + (line_len / 2) as f64 + 1.0) as i64
}

pub fn main() {
    let test = include_str!("../test.txt").lines().collect();
    let input = include_str!("../input.txt").lines().collect();
    println!("--- TEST ---");
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", calc(&test, 1));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", calc(&test, 2));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
    println!("--- REAL ---");
    let start_time = std::time::Instant::now();
    println!("Part 1: {}", calc(&input, 1));
    println!("Time: {:?}", start_time.elapsed());
    let part2_start_time = std::time::Instant::now();
    println!("Part 2: {}", calc(&input, 2));
    println!("Time: {:?}", part2_start_time.elapsed());
    println!("Total time: {:?}", start_time.elapsed());
}
