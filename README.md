# Advent of code 2017

## Convenience methods and classes
I have a small library with some convenience methods used for other AoC exercises. Like the ```ResourceLines``` class 
to read a resource file and transform the content into a ```List<String>```, or the CSV class to read a resource 
file containing comma separated values and returning a List of these values, optionally after transformation from 
```String``` to ```Integer```.

Also uses the algorithms library, which contains generic classes for addressing classic compute problems (from the book 
**Classic Computer Science Problems In Java** (c) Manning.com - 2020) 

## Day 1 
The tricky part of this exercise is traversing the list of integers and selecting the right number for the comparison.
I choose to create a base class ```IntProducer``` to traverse the list with a method ```get()``` that returns the 
integer to inspect and method ```next()``` to compare it to.

For the second part of the exercise, a derived class ```IntProducer2``` with a different ```next()``` method is 
being used.

## Day 2
Maybe not the most efficient, but I decided to represent the matrix as a ```List``` of ```List's```, which is easy to 
transform using the stream API. A class to host the convenience methods for transforming the content wrapped the matrix.

For part one, just create a List with the min values per row, and a List with the max values per row. I calculated the 
checksum by combining the two lists. For part two, I added a method to create a List of List's wich contained the 
easy dividable values per row.

## Day 3
Looking at the spiral, you can also see squares, where the outer square starts at the right of the last number of the 
inner square. The inner square has size 1x1, the next square has size 3x3, and each next square is 2 elements bigger 
in size, containing 8 more numbers than the inner square.

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
Finding double words is easy ... just add all words to a ```Set<String>``` and as soon as adding a word fails, you 
bumped into an invalid double word. That's all for part 1.

Doing so for anagrams is easy as well, as an anagram contains the same letters ... so just sort the letters in the 
word before you add the word to the set. As soon as you add an anagram, the sorted version of all anagrams are 
identical. So failing to add a sorted version of a word, means you aleady have an anagram in the set.

## Day 5
Create an array/list for the jump values. Use an 'instruction pointer' to navigate through the list. While counting the 
iterations, Keep iterating and change the jump-value in the list, after the new location has been determined.

For part 1 and 2, the only difference is in changing the value after calculating the jump offset. This can be 
configured using a class property, or a lambda you provide to the navigating class.

## Day 6 
Again you can use a Set to determine when a value reoccurs. In that Set, store the ```List<Integer>``` that represent
the rebalanced memory bank configurations. Just rebalance, add to the Set, and count the number of rebalancing 
performed. Pretty straight forward.

## Day 7
I used a ```Map``` to record child parent relationships, where the key is the program name, and the value is the
name of the parent (or null if parent is unavailable). The root element for part 1 is the entry in the map with a 
```null``` value.

For part 2, I added a Map with the weights per program. A convenience method gets the weight for a program, or the 
total weight of a program including the weight of its children.

Find an unbalanced child by grouping the total weights of it's children into a Map (key is weight, value is number of 
occurrences of that weight). The Map entry with value 1 is the unbalanced one. When all children of the unbalance tower
have the same weight, then the issue is at the parent tower, otherwise navigate to the child tower with the 
unbalanced weight. 

## Day 8
The trick for day 8 is to parse the instructions and feed them to a CPU class which updates (and creates) the registers
in case the predicate is true. For part 2, the CPU simply needs to track the highest possible register value.

## Day 9
Create a state machine that parses the input data. '<' moves into COMMENT state (all characters parsed in this state) 
are added to the comment string. '>' exits the comment state, and adds the comment to the comment list.
'!' increases a counter and enters EXCLUDE state. The next character parsed during EXCLUDE state, exits the EXCLUDE 
state again. '{' starts a GROUP state (when not in a comment), and increases the group-level. '}' exits the group state, 
updates the score and decreases group level again.

The stats gathered during parsing can be used to answer part 2 as well.

## Day 10
A KnotHash class wraps the array of values. It can reverse using a size parameter, and then reverses 'size' elements 
from the current element. A convenience methods takes care of this reverse operation on the list. 

```sparseHash```, ```denseHash``` and ```hexadecimal``` methods the operate on the KnotHash list to calulate the 
hexadecimal string for part 2.

## Day 11
You can consider a hex grid as a special type of square grid. The starting cell has coordinates (0,0). a cell at the 
north has coordinates (x, y+2), while a cell at the south has coordinates (x, y-2). A cell at north-east has 
coordinates (x+1,y+1), at south-east (x+1,y-1), at north-west (x-1, y+1), and at south-west (x-1, y-1).

Now calculating Manhattan distance is simple, although you need to take into account that you can move up along y-axis
with 2 in only one step. Next, for each move along the x-axis you get 1 step up or down for free,

Now you only need to parse the input, determine all coordinates based on the moves. The last coordinate after parsing 
the input is where the child is, so determine the distance from origin to the last coordinates.

For part two, just store the coordinates of the max distance while moving through the grid.   

## Day 12
Standard graph operations, so added the algorithms library which contains a basic Graph implementation. Some 
additional work was needed, as the input contains bi-directional edges, while the Graph implementation automatically
creates bi-directional edges. So, I needed to check for existence of the edges before adding them.

After this, creating groups (```Set<Program>```) for a specific vertex (Program) or all vertices is simple, as long as 
you remember which vertices have been visited before. 

## Day 13
I expect you should be able to calculate if you get caught in the firewall while passing. However, I choose
to write a simulation and run it, basically brute force the solution. It works well for part 1, and of course part 
two comes with a twist.

It takes too much time to run the simulation if you also simulate the delays when they increase above 100.000. First,
I refactored the creation of the Firewall for each try, and stopped the passing at the very first catch in any layer. 
That didn't do the trick, and the program also needed an optimization to skip delays, after all, it's pointless to 
delay a layer with a range of 15 more 1000 times or more.  

## Day 14
Part 1 is straight forward ... generate 128 Knot hash values, decode the hex into binary and count all 1. 
```Character.digit``` and ```Integer.toBinaryString``` are quite helpfull.
For part 2 I transformed the bit matrix into an ```int[][]``` and replaced all 1 bits with their sequence number. Then 
I traversed the matrix again and any higher sequence number next to a lower sequence number, got replaced with the 
lower sequence number.   

## Day 15
Hmmm ... I'm not sure where the catch in this one is ... the large numbers are no issue at all. Just an easy star 
for once? 

## Day 16
Okay, where large numbers were no issue on day 15, now they are. Until you consider that the result after each rounds 
might at some point start to repeat... and indeed after xx dances (where one dance contains all moves as described by 
the input) the results start to repeat. So, create a list of results (including result 0, the initial value) and the 
result after 1 bilion repetitions will equal the result after (1.000.000.000 % repeatListSize) ...   

## Day 17
A simple update on a ```List``` works well for part 1. For part two however, you need to add too many values to use 
a ```List```. However, the answer will be the value after the 0 (always the very first value in the list). So basically, if 
you would only remember the value inserted when current = 0, that would be the right answer. All other inserted 
values can be ignored.

## Day 18
Now this was a fun challenge ... I didn't want to introduce Threads, for that would also require correct 
synchronization. I'll keep that for another day. As the "compiler" methods are in the same class that run the 
instructions as Lambda's, having the Lambda's correct for part 2 is vital. A lambda compiled by CPU0, when executed
in the context of CPU1 might still access variables of CPU0... a slightly different design with a seperate
compiler class would have prevented that. A good test will detect these failures. Initially I was too lazy to
write that test ... :-(

## Day 19
And ... another fun challenge. Not that complicated though, once you have created a list of "tubes" with data 
on their start and end point, and the list of letters they might contain. For building the list, the challenge
is in determining the right direction, which is a bit more difficult for a tube starting at a crossing (+ sign).
 
  


