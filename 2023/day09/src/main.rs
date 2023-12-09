// mod grid;

pub fn solve(input: &str) -> () {
    let sequences: Vec<Vec<i64>> = input
        .lines()
        .map(|line| line.split(' ').map(|s| s.parse().unwrap()).collect())
        .collect();
    let mut sums_first = 0;
    let mut sums_last = 0;
    for seq in sequences {
        let mut layers: Vec<Vec<i64>> = Vec::new();
        layers.push(seq);
        loop {
            let prev_layer = layers.last().unwrap();
            let layer = prev_layer
                .windows(2)
                .map(|w| w[1] - w[0])
                .collect::<Vec<i64>>();
            layers.push(layer.clone());
            if layer.iter().all(|i| i == &0) {
                break;
            }
        }
        // println!("{:?}", layers);
        let mut sub_first = 0;
        let mut add_last = 0;
        for layer in layers.iter().rev() {
            sub_first = layer.first().unwrap().clone() - sub_first;
            add_last += layer.last().unwrap().clone();
        }
        sums_first += sub_first;
        sums_last += add_last;
        // println!("{}", add_last)
    }
    println!("Part 1: {}", sums_last);
    println!("Part 2: {}", sums_first);
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    let start = std::time::Instant::now();
    solve(input);
    println!("Finished in {} us", start.elapsed().as_micros());
}
