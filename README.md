# Word-Frequency
Project that designs a hashMap to store the information of words along with their frequencies for all words in a document. 
Web search engines use a variety of information in determining the most relevant documents to a query.  One important factor (especially in early search engines) is the frequency of occurrences of the query words in a document.  In general, one can try to answer a question how similar or dissimilar two documents are based on the similarity of their word frequency counts (relative to the document size).   A necessary step in answering these types of questions is to compute the word frequency for all words in a document. 
 
In this Project, a method wordCount(String input_file, String output_file) reads a file (test.txt) and prints out (into testOutput.txt) all the words encountered in the document along with their number of occurrences in the document with an output format such as “(father 30) (fishing 12) (aspirin 45) …”.  Assumption: Any derivative words taken to be distinct, e.g., “book” and “books”, “eat” and “eating” are all considered distinct. Also, words are defined to be simply strings of characters between two delimiting characters, which include a space and punctuation characters.  For example: something like “Father’s” is two words (“Father” and “s”, because they are separated by delimiters).  

Methods:

1.fileRead(): Method to read the Input file 
2.writeFile(): Method to write the information of words and their frequencies on text file
3.splitString():Helper method which extracts words from String of characters. Words formed by separating characters from delimiters
4.putInTable(): Helper method to put the list of strings of words from input file into hashTable. And while adding also checking the load factor each time
5.add(): Helper method to add a word in hashTable
6.rehasing(): Helper method to resize the hashTable. Creates a new table of double the size. Recalculates the indexes of inserted elements. And puts them into the newely designed table
7.checkLoadFactor(): Method to check for the load factor. (Load factor = number of items/hashTable size). For optimized chaining, load factor should be less than 1



How Project works:

1. Scan in the next word 
2. Search for this word in the hash table 
3. If not found, insert the new entry with this word and the initial count of 1. Otherwise increment the count. 
4. If inserted a new word, check if the hash table needs to be expanded. After processing the entire file, loop through the entire hash table and print out, list of words and their counts.   Also, the average length of the collision lists in the final state of hash table (across all hash slots, so empty slots also contribute). 
5. In implementing hash table, for hashFunction: Java’s hashCode function on strings h = Math.abs(word.hashCode()) % tableSize is used. 
 
