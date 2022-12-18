package day_17_2022

import java.io.File

fun solve(data: List<Char>) {

        // this is the list of the 5 rock types with helpful info to know how much
        // they can be moved left/right before crashing into the wall and the
        // default shift when they start
        data class Rock(val maxX: Int, val default: Int, val bits:List<Int>) {
            val height = bits.size
        }

        val rocks = listOf(
            Rock(3, 1, listOf(0b0001111)),
            Rock(4, 2, listOf(0b0000010, 0b0000111, 0b0000010)),
            Rock(4, 2, listOf(0b0000111, 0b0000001, 0b0000001)),
            Rock(6, 4, listOf(0b0000001, 0b0000001, 0b0000001, 0b0000001)),
            Rock(5, 3, listOf(0b0000011, 0b0000011)),
        )

        // prints a cave in a readable format
        fun caveDump(cv:List<Int>) {
            cv.forEach { println(it.toString(2).padStart(7, '0')) }
            println("-------")
        }

        // the data structure of a solution - the fill level and a state hash
        data class State(val fill:Int, val hash:String)

        // the hash function combines the index in the jet input, the last rock type and the front line
        // the front line is the height of the highest block in each of the seven columns relative to the highest
        fun hash(typ:Int, jet:Int, frnt:List<Int>):String = "$jet-$typ-${frnt.fold(""){s, i -> "$s$i|" }}"

        // I also observe how many rocks fall for each cycle of jet commands. If this stabilizes periodically
        // multiples of it are a good guess for the overall repetition of state
        var jetFreq = 0

        // simulate the fall of n rocks
        fun simulate(n:Int):List<State> {
            // prep the cave
            val cave = mutableListOf(0b01111111)
            cave.addAll(List(7){0b0})
            var fill = 1
            var rockTyp = 0
            var rock = rocks[0]
            var rLvl = 4
            var xPos = rock.default
            var jetIx = 0

            fun crash():Boolean {
                rock.bits.forEachIndexed() { ix, it -> if ((it shl xPos) and (cave[rLvl + ix]) > 0) return true }
                return false
            }
            val states = mutableListOf<State>()
            val front = MutableList(7){ 0 }

            var rollOver = 0

            // run the simulation
            for (ix in 0 until n) {
                do {
                    val oldXPos = xPos
                    xPos = if (data[jetIx] == '<') minOf(rock.maxX, xPos+1) else maxOf(0, xPos-1)
                    if (crash()) xPos = oldXPos

                    // now move down and increment the jet
                    rLvl -= 1
                    if (jetIx == data.size - 1) {
                        if (rollOver == 0) rollOver = states.size else if (jetFreq == 0) jetFreq = states.size - rollOver
                        jetIx = 0
                    } else jetIx++

                    // if that move down has crashed, stop falling and move back up
                } while (!crash())
                rLvl += 1

                // compute the new fill level based on the rock height and final position
                // and ensure enough headroom to run the crash logic
                val oldFill = fill
                fill = maxOf(fill, rLvl + rock.height)
                cave.addAll(List(fill + 7 - cave.size){0b0})

                // move the existing relative front line down
                (0 until 7).forEach { front[it] += (fill - oldFill) }

                // add the rock permanently to the cave
                for ((bix,b) in rock.bits.withIndex()) {
                    val new = b shl xPos
                    cave[rLvl + bix] = cave[rLvl + bix] or new

                    // add relevant new rock positions to the relative front-line
                    (0 until 7).forEach {
                        if ((1 shl it) and new > 0) {
                            front[it] = fill - rLvl - 1 - bix
                        }
                    }
                }

                // save the state for later analysis
                states.add(State(fill-1, hash(rockTyp, jetIx, front)))

                // increment and position the next rock
                rockTyp = (rockTyp + 1) % 5
                rock = rocks[rockTyp]
                rLvl = fill + 3
                xPos = rock.default

            }
            return(states)
        }

        // I compute 40_000 rocks as I need a bit more for part 2
        // since I keep state, I can get part 1 just from the state table
        // I estimated 40_000 since a previous run showed the jet frequency to be on the order of 2_000
        val states = simulate(10_000)
        print("\n----- Part 1 -----\n${states[2021].fill}")

        // now try to find a frequency where the front line, rock type, and jet index i.e. the hash are the same
        // looping through potential frequencies by trying multiples of the above detected jet frequency
        freq@for (freqMultiplier in 1..10) {
            val freq = freqMultiplier * jetFreq

            // now loop over the states and try to find the first time a hash is repeated after the frequency
            // the limits are chosen based on the expectation that the first repetition happens within 10* frequency
            for (ix in 1 until minOf(states.size - freq, freq*10)) {

                // this is a hit - we cracked it!
                if (states[ix].hash == states[ix+freq].hash) {

                    // compute the fill that is added for each repetitive period
                    val delta = states[ix+freq].fill - states[ix].fill

                    // use integer division and modulo and such to construct the total fill level
                    val test  = 1_000_000_000_000L - ix
                    var total = (test / freq) * delta
                    total += states[(test % freq).toInt() + ix - 1].fill // combines the first and last odd bit
                    print("\n----- Part 1 -----\n$total")
                    return
                }
            }
        }
    }


fun main(){
    solve(File("2022/inputs/Day_17.txt").readLines()[0].toList())
}