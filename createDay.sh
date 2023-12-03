#!/bin/zsh

PROVIDED_DAY=$1
TITLE=$2
YEAR=2023

if [ "$PROVIDED_DAY" -lt 10 ]; then
    DAY="0${PROVIDED_DAY}"
else
    DAY="$PROVIDED_DAY"
fi

mkdir -p ./${YEAR}/day${DAY}

touch ./${YEAR}/day"${DAY}"/input.txt

cp -r ./template/* ./${YEAR}/day"$DAY"

sed -i "s/template/day${DAY}/" "./${YEAR}/day${DAY}/Cargo.toml"
sed -i "s/template/day${DAY}/" "./${YEAR}/day${DAY}/Cargo.lock"

# sed -i "s/package template/package day_${DAY}_${YEAR}/; s/input.txt/Day_$DAY.txt/" "./${YEAR}/main/day_$DAY/Main.kt"

printf "# Advent Of Code ${YEAR} Day %s: %s\n\n## Part 1\nTBD\n\n---\n## Part 2\nTBD" "$DAY" "$TITLE" > ./${YEAR}/day"$DAY"/README.md

printf "\n[**Day %s: %s**](day%s/) - [AdventOfCode](https://adventofcode.com/${YEAR}/day/%s)\n" "$PROVIDED_DAY" "$TITLE" "$DAY" "$PROVIDED_DAY" >> ./${YEAR}/README.md

printf "target/\ninput.txt" > ./${YEAR}/day${DAY}/.gitignore

curl --cookie "session=$AOC_COOKIE" https://adventofcode.com/${YEAR}/day/"$PROVIDED_DAY"/input > ./${YEAR}/day"${DAY}"/input.txt
