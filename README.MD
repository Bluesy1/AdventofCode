# Advent of Code

This repository contains my solutions for all [Advent of Code](https://adventofcode.com/) puzzles.

## Years

- [2015](./prev/2015/) - Kotlin
- [2016](./prev/2016/) - Kotlin
- [2022](./prev/2022/) - Kotlin
- [2023](./prev/2023/) - Rust
- [2024](./solutions/2024/) - Rust


## Automated Input Download

This repo does (to the best of my abilities) follow the automation guidelines on the /r/adventofcode community wiki [listed here](https://www.reddit.com/r/adventofcode/wiki/faqs/automation). Specifically:

- There is only ever one outbound request per day
- The input is only downloaded once, when a day's file is created [End of `./start`](./start)
- The User-Agent header in [End of `./start`](./start) is set to me (with a private email in a `.email` file not checked into source control) since I am the one running the code.
- The session cookie is stored in a `.session_cookie` file that is not checked into source control. The cookie is only ever used to download my input and is never shared with anyone else.
- Inputs are not checked into source control.
