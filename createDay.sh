#!/bin/zsh

PROVIDED_DAY=$1
TITLE=$2

if [ "$PROVIDED_DAY" -lt 10 ]; then
    DAY="0${PROVIDED_DAY}"
else
    DAY="$PROVIDED_DAY"
fi

mkdir -p ./2023/day${DAY}

touch ./2023/day"${DAY}"/input.txt

cp -r ./template/* ./2023/day"$DAY"

sed -i "s/template/day${DAY}/" "./2023/day${DAY}/cargo.toml"
sed -i "s/template/day${DAY}/" "./2023/day${DAY}/cargo.lock"

# sed -i "s/package template/package day_${DAY}_2023/; s/input.txt/Day_$DAY.txt/" "./2023/main/day_$DAY/Main.kt"

printf "# Advent Of Code 2023 Day %s: %s\n\n## Part 1\nTBD\n\n---\n## Part 2\nTBD" "$DAY" "$TITLE" > ./2023/day"$DAY"/README.md

printf "\n[**Day %s: %s**](day%s/) - [AdventOfCode](https://adventofcode.com/2023/day/%s)\n" "$PROVIDED_DAY" "$TITLE" "$DAY" "$PROVIDED_DAY" >> ./2023/README.md

curl --cookie "session=$AOC_COOKIE" https://adventofcode.com/2023/day/"$PROVIDED_DAY"/input > ./2023/day"${DAY}"/input.txt
