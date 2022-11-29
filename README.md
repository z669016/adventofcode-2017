# Advent of code 2017

## Convenience methods and classes
I have a small library with some convenience methods used for other AoC exercises. Like the ```ResourceLines``` class 
to read a resource file and transform the content into a ```List<String>```, or the CSV class to read a resource 
file containing comma separated values and returning a List of these values, optionally after transformation from 
```String``` to ```Integer```.

Also uses the algorithms' library, which contains generic classes for addressing classic compute problems (from the book 
**Classic Computer Science Problems In Java** (c) Manning.com - 2020) 

It was never my intention to create the shortest program possible. I did try to create clear and simple implementations.

## Day 1 
The tricky part of this exercise is traversing the list of integers and selecting the right number for the comparison.
I choose to create a base class ```IntProducer``` to traverse the list with a method ```get()``` that returns the 
integer to inspect and method ```next()``` to compare it to.

For the second part of the exercise, a derived class ```IntProducer2``` with a different ```next()``` method is 
being used.

## Day 2
Maybe not the most efficient, but I decided to represent the matrix as a ```List``` of ```List's```, which is easy to 
transform using the stream API. The class ```Spreadsheet``` to host the methods for performing the calculations.

For part 1, just create a List with the min values per row, and a List with the max values per row. I calculated the 
checksum by zipping the two lists (using Guava ```Streams.zip```) and summing the absolute values of the difference
within each pair of values. For part 2, I added a method to create a List of ```Pair<Integer,Integer>```s which 
contained the easy dividable values per row, and then sum the division of each pair.

## Day 3
Looking at the spiral, you can also see squares, where the outer square starts at the right of the last number of the 
inner square. The inner square has size 1x1, the next square has size 3x3, and each next square is 2 elements larger, 
containing 8 more numbers than the inner square.

It takes some puzzling, but based on that pattern, when value 1 is at coordinates (0,0), you can calculate the sequence
number for any coordinate of the matrix, and v.v. 

Once the class to make these transformations works, just calculate the coordinates from the input, and calculating 
the Manhattan distance is piece of cake.

These calculations can be reused for part 2. To calculate the value for a next cell, you need to know the values of 
the cells before it on the spiral. To speed things up I created an array of values starting with the very first element.
For each next element, determine the coordinates of all surrounding cells, and remove from that list all cells with a 
sequence number higher than that of the current cell. The values of the lower sequence numbers must be in the array 
already, so these can be used to calculate the next value (which in turn, is added to the array). Just keep doing this, 
until you calculated a value larger that the input value. 

## Day 4
Finding double words is easy ... just add all words to a ```Set<String>``` and as soon as adding a word fails 
(Set.add returns false is the element already exists in the set), you bumped into an invalid double word. That's all 
for part 1.

Doing so for anagrams is easy as well, as an anagram contains the same letters ... so just sort the letters in the word 
before you compare. The sorted letter version of all anagrams are identical. So failing to add a sorted letter version 
of a word, means you already have an anagram for that word in the set.

## Day 5
Create an array/list for the jump values (class ```Jumper```). Use an 'instruction pointer' to navigate through the 
list. While counting the iterations, Keep iterating and change the jump-value in the list, after the new location 
has been determined.

For part 1 and 2, the only difference is in changing the value after calculating the jump offset. This can be 
configured using a class property, or a lambda you provide to the navigating class.

## Day 6 
Again you can use a Set to determine when a value reoccurs. In that Set, store the ```List<Integer>``` that represent
the rebalanced memory bank configurations. Just re-balance, add to the Set, and count the number of re-balancing 
performed. Pretty straight forward.

For part 2, use a ```Map<List<Integer>,Integer>``` to store the re-ordered configurations and the loop-count of their 
first 'appearance' (using the configuration as the key). As soon as any configuration reoccurs, it's original count 
is available in the map, and you can calculate the cycles it took before it reoccurred.  

## Day 7
I used a ```Map``` to record child parent relationships, where the key is the program name, and the value is the
name of the parent (or null if parent is unavailable). A root element (part 1) is any entry in the map with a 
```null``` value.

For part 2, I added a Map with the weights per program. A convenience method gets the weight for a program, or the 
total weight of a program including the weight of its children.

Find an unbalanced child by grouping the total weights of its children into a Map (key is weight, value is number of 
occurrences of that weight). The Map entry with value 1 is obviously the unbalanced one. When all children of the 
unbalanced tower do have the same weight, then the issue is at the parent tower, otherwise navigate to the child tower 
with the unbalanced weight. 

## Day 8
The trick for day 8 is to parse the instructions and feed them to a ```CPU``` class which updates (and creates) the 
registers in case the predicate is true. For part 2, the CPU simply needs to track the highest possible register value
in the ```setRegister()``` method.

## Day 9
Create a state machine that parses the input data. '<' moves into COMMENT state (all characters parsed in this state) 
are added to the comment string. '>' exits the comment state, and adds the comment to the comment list.
'!' increases a counter and enters EXCLUDE state. The next character parsed during EXCLUDE state, exits the EXCLUDE 
state again. '{' starts a GROUP state (when not in a comment), and increases the group-level. '}' exits the group state, 
updates the score and decreases group level again.

The ```DataTokenizer``` class wraps the input and supports the ```Iterator<DataToken>``` interface iterate the input 
string.```DataStats.parse()``` parses an input string, calculates the score, the garbage length and counts all excluded
characters and returns a DataStats instance. The stats gathered during parsing can be used to answer both part 1 and 2.

## Day 10
A KnotHash class wraps the array of values. It can reverse using a size parameter, and then reverses 'size' elements 
from the current element. A convenience method takes care of this reverse operation on the list. 

```sparseHash```, ```denseHash``` and ```hexadecimal``` methods operate on the KnotHash list to calculate the 
hexadecimal string for part 2.

## Day 11
You can consider a ```HexGrid``` as a special type of square grid. The starting cell has coordinates (0,0). a cell at the 
north has coordinates (x, y+2), while a cell at the south has coordinates (x, y-2). A cell at north-east has 
coordinates (x+1,y+1), at south-east (x+1,y-1), at north-west (x-1, y+1), and at south-west (x-1, y-1).

Now calculating Manhattan distance is simple, although you need to take into account that you can move up along y-axis
with 2 in only one step. Next, for each move along the x-axis you get 1 step up or down for free,

Now you only need to parse the input, determine all coordinates based on the moves. The last coordinate after parsing 
the input is where the child is, so determine the distance from origin to the last coordinates.

For part two, just store the coordinates of the max distance while moving through the grid.   

## Day 12
Standard graph operations, so added the algorithms library which contains a basic Graph implementation. Some 
additional work was needed for the ```ProgramGraph<Program>```, as the input contains bidirectional edges, while the 
Graph implementation (```UnweightedGraph<T>```) automatically creates bidirectional edges. So, I needed to check for 
existence of the edges before adding them.

After this, creating a group (```Set<Program>```) for a specific vertex (```Program("0")```) of all vertices is simple, 
use a queue to browse vertices and their neighbours one by one, just remember which vertices have been visited before 
to prevent you run around in circles. This will solve part 1.

For part 2, just browse all vertices in the graph, and get their group (reuse method of part 1) and store them in a 
```Set<Set<Program>>```. Again, remember which vertices have been visited before to prevent you run around in circles.

## Day 13
I expect you should be able to calculate if you get caught in the firewall while passing. However, I choose
to write a simulation and run it using a ```Firewall``` that contains ```FirewallLayer```s, basically brute force the 
solution. It works well for part 1, and of course part two comes with a twist.

It takes too much time to run the simulation if you also simulate the delays when they increase above 100.000. First,
I refactored the creation of the Firewall for each try, and stopped the passing at the very first catch in any layer. 
That didn't do the trick, and the program also needed an optimization to skip delays, after all, it's pointless to 
delay a layer with a range of 15 for 1000 times (or more).  

## Day 14
Part 1 is straight forward ... generate 128 Knot hash values, decode the hex into binary and count all 1. 
```Character.digit``` and ```Integer.toBinaryString``` are quite helpful.

For part 2, I transformed the bit matrix into an ```int[][]``` and replaced all 1 bits with a sequence number. Then 
I traversed the matrix again and any higher sequence number next to a lower sequence number, got replaced with the 
lower sequence number. By first assigning a sequence number and then clustering them into connected regions, I can 
distinguish the individual regions with different numbers, and count the regions by getting all distinct region 
numbers.    

## Day 15
Hmmm ... I'm not sure where the catch in this one is ... the large numbers are no issue at all. Just an easy star 
for once? 

I created a ```Generator``` class which implements ```Supplier<Long>``` to generate values using a ```factor``` and 
a ```startValue```, and a Judge to compare the lower 16 bits (```value & 0xffff```). Then compare and count 40 million
values from both generators.

For part 2, I created a ```MultipleOfGenerator```, which inherits from Generator and adds the multiple-of clause to 
the ```get()``` method. Then run the same comparison 5 million times with two of this new generators.

## Day 16
Created a ```DanceLine``` class, with a ```dance()``` method which can take a list of moves, and process them. 
For part 1, process all the moves once, and you get the answer. 

Okay, where large numbers were no issue on day 15, now they are for part 2. Until you consider that the result after 
each round might at some point start to repeat... so, loop for 1 billion times and record the history (results of the
```DanceLine``` after each```dance()```), and break the loop when a repetition is found. 

And indeed after xx dances (history-size) the results start to repeat. The result after 1 billion repetitions will equal the result 
after (1.000.000.000 % history-size) ...   

## Day 17
A simple update on a ```List``` using a ```SpinLock``` implementation works well for part 1. 

For part 2 however, you need to add too many values to use a ```List```. However, the answer will be the value after 
the 0 (always the very first value in the list). So basically, if you would only remember the value inserted when 
current = 0, that would be the right answer. All other values can be ignored, and don't need to be remembered.
This was implemented in the ```SpinLockSimulation```.

## Day 18
Now this was a fun challenge ... create some kind of virtual machine. A ```CPU``` class takes the instruction and 
compiles it into functions that take a CPU instance and executes the instruction against the CPU state (its registers).
The ```CPU.play()```, takes a list of instructions, and executes them against itself.

For part 2, I didn't want to introduce Threads, for that would also require correct synchronization. I'll keep that for
another day. I've created a ```PairedCPU```, which can be "paired" to another CPU. The ```PairedCPU``` has different
behaviour for the ```snd()``` and ```rcv()``` methods, where it sends/receives output to/from the paired CPU. The 
```play()``` method returns ```true``` if it is not waiting (to receive) any input.

## Day 19
And ... another fun challenge. Not that complicated though, once you have created a list of "tubes" with data 
on their start and end point, and the list of letters they might contain. The input is processed by the
```SeriesOfTubes.of()``` factory method. For building the list, the challenge is in determining the right direction, 
which is a bit more difficult for a tube starting at a crossing (+ sign). Once you have the list of ```Tube```s,
you can use it to gather the letters (part 1), or calculate the number of steps (part 2). The number of steps for
each tube is simply the Manhattan distance between its start and end point. 

## Day 20
This required some thinking ... and validation math for part 1. The ```Particle``` that will in the end be closest to
the origin will be the one with the smallest acceleration (Manhattan distance). If two accelerations are equal, 
the one with the smallest delta between two steps will win, and if even those are equal, then the particle that 
starts closest to the origin will win. I wrapped this in a ```compareTo()``` method, create a list of ```Particle```s
and sort it in natural order. The first particle in the list will be the right answer for part 1.

Part 2 can probably be calculated, but you can also just simulate moving forward and after each step removing 
all Particles with the same position (to prevent unnecessary calculations). The stream API works well for this. The 
question is when to stop moving forward ... I decided to stop if the list didn't change for 1000 consecutive steps.
The size of the remaining list of particles is the answer for part 2.
  
## Day 21
A nasty one this time ... first I had a mistake in one of the rotations (indeed, didn't completely test them),
then I missed some combinations of flipping and rotating. Lost quite some time finding that issue.

I've created an ```Enhancer``` class, that loads a set of enhancer rules. It is capable of transforming a grid according
to the rules a number of times. You need to ensure you cover all possible rotation and flipping to find the right 
transformation rule for each enhancement. You need to cover no-flip and rotation, a horizontal flip anr rotation,
vertical flip and rotation, horizontal and vertical flip and rotation, and vertical and horizontal flip and rotation.

## Day 22
Pretty straight forward ... reusing the GidUtils class.

Created a ```Direction``` object with methods for turning the current direction. Created a ```Virus``` class to 
navigate the grid, determine next direction based on state of the current node, affect the current node, and keep 
counters. For part 2, just created a ```SmarterVirus``` (which inherits from ```Virus```) with different methods to 
determine the next direction, and affecting the current node. Even the 10.000.000 bursts of part 2, went pretty fast.

## Day 23
Part one is straight forward ... part 2 requires you to analyse the assembly code and determine what it does. It counts
the numbers between values of b and c which are non-prime numbers.

## Day 24
Quite simple if you use a kind of depth-first-search to create bridges (chains of components) from the options you 
have, and collect all possible bridges that cannot be extended any further from the components left. For part one,
just find the strongest. For part two, first find the length of the longest bridge, then do the max search again 
but filter on the right (longest) length first.

## Day 25
I decided not to write a parser to dynamically create the states, so I hardcoded my input. As the number of steps was 
high, I also decided to use a ```List<BitSet>```s as tape, where each bitset contains 64 positions (I started with a 
```List<Long>```, but BitSet was easier for it already provides the required operations to set the individual bits).
Using lambda's for the State operations makes the states, and the Turing machine stunningly simple. Counting the bits 
set for each bitset and adding them up was also simple. 

  
 
 