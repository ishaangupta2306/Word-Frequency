import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.*;
import java.lang.Character;
import java.lang.String;

/**
 * A class representing hashMap for storing the information of words in a text file
 * @autor Ishaan Gupta
 */
public class hashMap
{ 
  //ArrayList representing the hashTable
  private static ArrayList<LinkedList<Node>> hashTable = new ArrayList<>(16);
  //Field storing the count of elements in hashTable
  private static int number;
  //Field storing the tableSize
  private static int tableSize = 16;
  
  /**
   * A nested class stores the information of particular words & its frequency
   */
  private static class Node
  {
    private String word;
    private int frequency;
    
    public Node(String word, int frequency)
    {
      this.word = word;
      this.frequency = frequency;
    }
  }   
  
  /**
   * Helper method to add a word in hashTable
   * @param word 
   */
  private static void add(String word)
  {
    //If table is empty, initialize it
    if(hashTable.isEmpty())
    {
      for(int i = 0; i < tableSize; i++)
        hashTable.add(i,new LinkedList<Node>());
    }
    //Increasing the count
    number += 1;
    //calculation of the index
    int i = Math.abs(word.hashCode()) % tableSize; 
    
    boolean flag = false;
    //LinkedList at index i
    LinkedList<Node> a = hashTable.get(i);
    //Iterator for linkedList
    Iterator<Node> it = a.listIterator();
    //Local variable
    Node temp;
    
    //loop to search if the word to be inserted is already present in linkedList
    while(it.hasNext() && flag != true)
    {
      temp = it.next();
      if(temp.word.equals(word))
      {
        flag = true;
        //If word is found, increase its frequency
        temp.frequency += 1;
      }
    }
    //Word not found, new word needs to be added
    if(flag == false)
    {
      a.addLast(new Node(word,1));
    }
  }
  
  /**
   * Helper method to resize the hashTable
   * Creates a new table of double the size
   * Recalculates the indexes of inserted elements
   * And puts them into the newely designed table
   */
  private static void rehashing()
  {
    //New HashTable
    ArrayList<LinkedList<Node>> table = new ArrayList<LinkedList<Node>>(tableSize*2);
    
    //Loop to initialize the hashTable
    for(int i = 0; i < tableSize*2; i++)
      table.add(i,new LinkedList<Node>());
    
    //Loop to iterate over the elements 
    for(int i = 0; i < tableSize; i++)
    {
      //Loop to iterate over the linkedList
      for(Node n : hashTable.get(i))
      {
        //Calculation of new indexes and addition of elements
        int j = Math.abs(n.word.hashCode()) % (tableSize*2);        
        table.get(j).addLast(n);
      }
    }
    //Hashtable and tableSize are modified
    hashTable = table;
    tableSize*= 2;
  }
  
  /**
   * Method to check for the load factor 
   * Load factor = number of items/hashTable size
   * For optimized chaining, load factor should be less than 1
   * @return boolean
   * true means hashTable needs to be resized
   * false means no such need
   */
  private static boolean checkLoadFactor()
  {
    if(number/tableSize >=1)
      return true;
    return false;
  }
  
  /**
   * Helper method to put the list of strings of words from input file into hashTable
   * And while adding also checking the load factor each time
   * @param LinkedList<String> list
   */
  private static void putInTable(LinkedList<String> list)
  {
    for(String s: list)
    {
      add(s);
      if(checkLoadFactor() == true)
        rehashing();
    }
  }
  
  /**
   * Helper method which extracts words from String of characters
   * Words formed by separating characters from delimiters
   * @param StringBuilder input
   * @return LinkedList<String>: LinkedList used as addition/deletion of elements frequently is efficient 
   */
  private static LinkedList<String> splitString(StringBuilder input)
  {
    //Local list to store words
    LinkedList<String> list = new LinkedList<>();
    //Local string to form words
    StringBuilder temp = new StringBuilder();
    
    //Loop to iterate over the input string
    for(int i = 0; i < input.length(); i++)
    { 
      //If character is not a delimiter, append it to string
      if(Character.isLetter(input.charAt(i)))
      {
        //character converted to lowercase for easy comparison
        temp.append(Character.toLowerCase(input.charAt(i)));          
      }
      
      //Delimiter found, append the word to linkedList
      else
      { 
        if(!temp.toString().equals(""))
        {
          list.addFirst(temp.toString());
          //Reinitialization of StringBuilder variable
          temp = new StringBuilder();
        }
      }
    }
    return list;
  }
  
  /**
   * Method to read the Input file 
   * @param String name
   */
  private static void fileRead(String name)
  {
    try
    {
      FileInputStream f = new FileInputStream(name);
      int read = 0;
      StringBuilder s = new StringBuilder();
      //Loop to traverse through file character-by-character
      while((read = f.read()) != -1)
      {
        Character temp = (char)read;
        s.append(temp);
      }
      f.close();
      //words put in hashTable
      putInTable(splitString(s));
    }      
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
  
  /**
   * Method to write the information of words and their frequencies on text file
   * Also writes the Average number of collisions
   * @param String outputFile
   */
  private static void writeFile(String outputFile)
  {
    try
    {
      FileWriter writer = new FileWriter(outputFile);
      //Local variable to store number of nodes
      double count = 0;
      //Loop to iterate over each word in hashTable
      for(int i = 0; i < tableSize; i++)
      {
        LinkedList<Node> temp = hashTable.get(i);
        for(Node n: temp)
        {
          writer.write("(" + n.word  + " " + n.frequency + ")");
          count++;            
        }
      }
      writer.write("Average number of collisions = " + (count/tableSize)); 
      writer.close();
    }
    catch(Exception e)
    {
      System.out.println("Input file error");
    }
  }
  /**
   * Method that reads a file and prints all words along with their frequencies in another text file
   * @param String input_file
   * @param String output_file
   */
  public static void wordCount(String input_file, String output_file)
  {
    fileRead(input_file);
    writeFile(output_file);
  }
  
  /**
   * Main method
   * @param String args[]
   */
  public static void main(String args[])
  {
    //Try-catch if only one file name input
    try
    { 
      hashMap.wordCount(args[0],args[1]);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      System.out.println("Input two file names");
    }   
  }
  
}














