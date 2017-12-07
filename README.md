## Rage 2048

I try to counter the most common strategy used by players which is to have large tiles idle in a section while moving small tiles around.

To combat this, I will assign a lazyness index to each tile. If a tile remains idle, it's lazyness index increases while if a tile moves, it's lazyness index gets reseted. A new tile is spawned at the place which had the tile with the highest lazyness index.
There might be ways to still win the game though. But they will be hopefully a bit more non-trivial and would require rotation technique.

---

To load the game on windows, linux or osx, type

~~~~
javac Main.java
java Main
~~~~

The controls of the game are w-a-s-d. Press enter after each move.
