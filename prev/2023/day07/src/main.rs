use std::{cmp::Ordering, collections::HashMap};

use itertools::Itertools;

mod grid;

#[derive(PartialOrd, PartialEq, Eq, Debug)]
enum Type {
    FiveKind = 6,
    FourKind = 5,
    FullHouse = 4,
    ThreeKind = 3,
    TwoPair = 2,
    OnePair = 1,
    HighCard = 0,
}

impl Ord for Type {
    fn cmp(&self, other: &Self) -> Ordering {
        match (self, other) {
            (Type::FiveKind, Type::FiveKind) => Ordering::Equal,
            (Type::FiveKind, _) => Ordering::Greater,
            (_, Type::FiveKind) => Ordering::Less,
            (Type::FourKind, Type::FourKind) => Ordering::Equal,
            (Type::FourKind, _) => Ordering::Greater,
            (_, Type::FourKind) => Ordering::Less,
            (Type::FullHouse, Type::FullHouse) => Ordering::Equal,
            (Type::FullHouse, _) => Ordering::Greater,
            (_, Type::FullHouse) => Ordering::Less,
            (Type::ThreeKind, Type::ThreeKind) => Ordering::Equal,
            (Type::ThreeKind, _) => Ordering::Greater,
            (_, Type::ThreeKind) => Ordering::Less,
            (Type::TwoPair, Type::TwoPair) => Ordering::Equal,
            (Type::TwoPair, _) => Ordering::Greater,
            (_, Type::TwoPair) => Ordering::Less,
            (Type::OnePair, Type::OnePair) => Ordering::Equal,
            (Type::OnePair, _) => Ordering::Greater,
            (_, Type::OnePair) => Ordering::Less,
            (Type::HighCard, Type::HighCard) => Ordering::Equal,
        }
    }
}

impl Type {
    fn from_part1(hand: Vec<u8>) -> Self {
        let mut counts = [0; 13];
        for card in hand {
            match card {
                i if i < 13 => counts[i as usize] += 1,
                _ => unreachable!(),
            }
        }

        let mut pairs = 0;
        let mut three = false;
        let mut four = false;
        let mut five = false;
        for count in counts.iter() {
            match count {
                2 => pairs += 1,
                3 => three = true,
                4 => four = true,
                5 => five = true,
                _ => continue,
            }
        }

        if five {
            return Type::FiveKind;
        } else if four {
            return Type::FourKind;
        } else if three && pairs == 1 {
            return Type::FullHouse;
        } else if three {
            return Type::ThreeKind;
        } else if pairs == 2 {
            return Type::TwoPair;
        } else if pairs == 1 {
            return Type::OnePair;
        } else {
            return Type::HighCard;
        }
    }

    // Jokers are wild
    fn from_part2(hand: Vec<u8>) -> Self {
        let mut kind = 0;
        hand.iter().map(
            |card| match card {
                0 => vec![0,1,2,3,4,5,6,7,8,9,10,11,11],
                i if i < &13 => vec![(*i as u8) - 1],
                _ => unreachable!(),
            }
            )
            .multi_cartesian_product()
            .for_each(
            |cards| {
                let counts = cards.iter().fold([0; 12], |mut acc, card| {
                    acc[card.clone() as usize] += 1;
                    acc
                });
                let mut true_counts: HashMap<u8, u8> = HashMap::new();
                for count in counts.iter() {
                    if count > &0 {
                        *true_counts.entry(*count as u8).or_insert(0) += 1;
                    }
                }
                let this_kind = if true_counts.contains_key(&5) {
                    6
                } else if true_counts.contains_key(&4) {
                    5
                } else if true_counts.contains_key(&3) && true_counts.contains_key(&2) {
                    4
                } else if true_counts.contains_key(&3) {
                    3
                } else if true_counts.contains_key(&2) && true_counts.get_key_value(&2).unwrap().1 == &2 {
                    2
                } else if true_counts.contains_key(&2) && true_counts.get_key_value(&2).unwrap().1 == &1 {
                    1
                } else {
                    0
                };
                if this_kind > kind {
                    kind = this_kind;
                }
            }
        );
        match kind {
            6 => Type::FiveKind,
            5 => Type::FourKind,
            4 => Type::FullHouse,
            3 => Type::ThreeKind,
            2 => Type::TwoPair,
            1 => Type::OnePair,
            0 => Type::HighCard,
            _ => unreachable!(),
            
        }
    }
}

#[derive(PartialOrd, PartialEq, Eq, Debug)]
struct Hand {
    cards: Vec<u8>,
    kind: Type,
    score: usize,
    part1: bool,
}

impl Ord for Hand {
    fn cmp(&self, other: &Self) -> Ordering {
        return match self.kind.cmp(&other.kind) {
            Ordering::Greater => Ordering::Greater,
            Ordering::Less => Ordering::Less,
            _ => {
                for (a, b) in self.cards.iter().zip(other.cards.iter()) {
                    match a.cmp(b) {
                        Ordering::Equal => continue,
                        other => {return other;}
                    }
                }
                unreachable!()
            }
        };
    }
}

pub fn part1(input: &str) -> String {
    let mut cards = input.lines().map(|line| {
        let (hand, score) = line.split_once(" ").unwrap();
        let score = score.parse::<usize>().unwrap();
        let cards = hand.chars().map(|char|
            match char {
                'A' => 12,
                'K' => 11,
                'Q' => 10,
                'J' => 9,
                'T' => 8,
                '9' => 7,
                '8' => 6,
                '7' => 5,
                '6' => 4,
                '5' => 3,
                '4' => 2,
                '3' => 1,
                '2' => 0,
                _ => unreachable!(),
            }
        ).collect::<Vec<_>>();
        let type_ = Type::from_part1(cards.clone());

        Hand {
            cards,
            kind: type_,
            score,
            part1: true,
        }
    }).collect::<Vec<_>>();
    cards.sort_by(Hand::cmp);
    // cards.reverse();
    // for card in cards.iter() {
    //     println!("{:?}", card);
    // }
    cards.iter().zip(1..=cards.len()).fold(0, |acc, (hand, i)| {
        acc + hand.score * i
    }).to_string()
}

#[allow(unused_variables)]
pub fn part2(input: &str) -> String {
    let mut cards = input.lines().map(|line| {
        let (hand, score) = line.split_once(" ").unwrap();
        let score = score.parse::<usize>().unwrap();
        let cards = hand.chars().map(|char|
            match char {
                'A' => 12,
                'K' => 11,
                'Q' => 10,
                'T' => 9,
                '9' => 8,
                '8' => 7,
                '7' => 6,
                '6' => 5,
                '5' => 4,
                '4' => 3,
                '3' => 2,
                '2' => 1,
                'J' => 0,
                _ => unreachable!(),
            }
        ).collect::<Vec<_>>();
        let type_ = Type::from_part2(cards.clone());

        Hand {
            cards,
            kind: type_,
            score,
            part1: false,
        }
    }).collect::<Vec<_>>();
    cards.sort_by(Hand::cmp);
    cards.iter().zip(1..=cards.len()).fold(0, |acc, (hand, i)| {
        acc + hand.score * i
    }).to_string()
}

pub fn main() {
    // let input = include_str!("../test.txt");
    let input = include_str!("../input.txt");
    println!("Part 1: {}", part1(input));
    println!("Part 2: {}", part2(input));
}
