use rayon::iter::{IntoParallelRefIterator, ParallelIterator, IntoParallelIterator};

struct Transformation {
    from: usize,
    to: usize,
    len: usize,
}

impl Transformation {
    fn apply(&self, input: usize) -> Option<usize> {
        if input >= self.to && input < self.to + self.len {
            Some(self.from + (input - self.to))
        } else {
            None
        }
    }
}

impl From<String> for Transformation {
    fn from(input: String) -> Self {
        let parts = input
            .trim()
            .split(" ")
            .map(|s| s.parse::<usize>().unwrap())
            .collect::<Vec<_>>();

        Self {
            from: parts[0],
            to: parts[1],
            len: parts[2],
        }
    }
}

struct Mapper {
    transformations: Vec<Transformation>,
}

impl Mapper {
    fn transform(&self, input: usize) -> usize {
        // assume there's just one valid?
        self.transformations
            .iter()
            .find_map(|t| t.apply(input))
            .unwrap_or_else(|| input)
    }
}

impl From<String> for Mapper {
    fn from(input: String) -> Self {
        let lines = input.trim().lines().skip(1);
        Self {
            transformations: lines.map(|line| line.trim().to_string().into()).collect(),
        }
    }
}

fn parse_seeds(line: &str) -> Vec<usize> {
    let parts = line.trim().split(": ").collect::<Vec<_>>();
    parts[1]
        .split(" ")
        .map(|s| s.parse::<usize>().unwrap())
        .collect::<Vec<_>>()
}


pub fn part1(input: &str) -> String {
    let portions = input.trim().split("\n\n").collect::<Vec<_>>();
    let seeds = parse_seeds(portions[0]);
    let maps = portions[1..]
        .iter()
        .map(|s| Mapper::from(s.to_string()))
        .collect::<Vec<_>>();
    seeds
        .par_iter()
        .map(|s| {
            let mut value = *s;
            for map in maps.iter() {
                value = map.transform(value);
            }
            value
        })
        .min()
        .unwrap()
        .to_string()
}

pub fn part2(input: &str) -> String {
    
    let portions = input.trim().split("\n\n").collect::<Vec<_>>();

    let fake_seeds = parse_seeds(portions[0]);

    let maps = portions[1..]
        .iter()
        .map(|s| Mapper::from(s.to_string()))
        .collect::<Vec<_>>();
    
    let seeds: Vec<_> = fake_seeds
        .chunks(2)
        .map(|ch| (ch[0]..ch[0] + ch[1]))
        // .flatten()
        .collect();
    seeds
        .iter()
        .map(|range| {
            range.clone().into_par_iter()
                .map(|s| {
                    let mut value = s;
                    for map in maps.iter() {
                        value = map.transform(value);
                    }
                    value
                })
                .min()
                .unwrap()
        })
        .min().unwrap().to_string().into()
}

pub fn main() {
    let input = include_str!("../input.txt");
    // let input = include_str!("../test.txt");
    println!("Part 1: {}", part1(input));
    println!("Part 2: {}", part2(input));
}
