## Rage 2048

I try to counter the most common strategy used by players which is to have large tiles idle in a section while moving small tiles around.

To combat this, I will assign a lazyness index to each tile. If a tile remains idle, it's lazyness index increases while if a tile moves, it's lazyness index gets reseted. A new tile is spawned at the place which had the tile with the highest lazyness index.

There might be ways to still win the game though. But they will be hopefully a bit more non-trivial and would require rotation technique.

They game may feel same initially but (hopefully) gets more difficult

---

To load the game, nagivate to the correct directory and type

~~~~
javac Main.java
java Main
~~~~

The controls of the game are w-a-s-d. Press enter after each move.

---

The game is completely deterministic which means that if you do the same moves, you get the same outcomes everytime. You can take advantage of this and chain inputs. For example, multiple inputs like `waddssw` are valid.
