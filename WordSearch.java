import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

/*
 * [WordSearch.java]
 * Takes in a word search(maximum of ten words) as a txt file, completes it and returns the completed
 * version in an html file
 * @author Alex Souphanthong
 * @version 1.0, December 3, 2020
 * 
 */

class WordSearch{

 public static void main(String[] args) throws Exception{
     

    //Loads the file
     File wordSearch = new File("wordsearch.txt");
     Scanner readFile = new Scanner(wordSearch);

     int rows, columns;
     String currentLine ="";
     String currentWord;
     String [] wordsFound = new String[10];
     int counter = 0;




     //Take in the size of the array
     rows = readFile.nextInt();
     columns = readFile.nextInt();
     readFile.nextLine();

 
    //Initalize the board
     String [][] board = new String[rows][columns];
     

     //Saves the word search as a 2D array
     for(int i = 0; i < rows; i++){
         
       if(readFile.hasNext()){
         currentLine = readFile.nextLine();
    
       }
        
       
       for(int count = 0; count < columns; count++){
           
         board[i][count] = currentLine.substring(0,1);       
         currentLine = currentLine.substring(currentLine.indexOf(" ") +1);
            

       }
    }

      
     //Loops through all the words to search for
     while(readFile.hasNext() && counter < 10){
       
       //Searches for the words within the word search
       currentWord = readFile.nextLine();
       
       board = findHorizontal(board, currentWord);
       board = findVertical(board, currentWord);
       board = findForwardDiag(board, currentWord);
       board = findBackwardDiag(board, currentWord);
       wordsFound[counter] = currentWord;
       counter++;
       
       //If a word exist as a subset of another word look for that word again
       for(int i = 0; i < counter-1; i++){
         if(currentWord.indexOf(wordsFound[i]) != -1){
           board = findHorizontal(board, wordsFound[i]);
           board = findVertical(board, wordsFound[i]);
           board = findForwardDiag(board, wordsFound[i]);
           board = findBackwardDiag(board, wordsFound[i]);
           
         }
       }
         
       
     }
     
     
    //Output the completed word search to an HTML file 
    try{
      outputHTMLFile(board, wordsFound);

    }catch (Exception e){
      System.out.println("Error");
    }
     
      
    
      //Outputs the completed word search on the console
      for(int count = 0; count < board.length; count++){
        System.out.println();

        for(int i = 0;i < board[count].length;i++){
          
          
         System.out.print(board[count][i] + " ");
         
            
        }
        
        
       }

       System.out.println();
       System.out.println("Words to Find:");
       for(int i = 0; i < wordsFound.length; i++){
         if(wordsFound[i]!= null){
         System.out.println(wordsFound[i]);
       }
      
     
     
     readFile.close();


 }

 }

 

  /*
  * findVertical
  * This method accepts a word search and a word to search for. It finds the first vertical instance of a word
  * @param board a 2d array of the board
  * @param word a string of the word to search for
  * @return the word search with the found word capatailzed or the same board if it is not
  */
 public static String[][] findVertical(String[][]board, String word){
   
   String currentWord = "";
   int rowLength = board.length;
   int columnLength = board[0].length;
   int startNum;
   int row;
   int column;
   
   
   //Loops through the rows
   for(int i = 0; i < board[0].length; i++) {
     
     //Loops through the columns
     for(int x = 0; x < board.length; x++){
      
       currentWord = "";
       
       //Loops through and create all possible words that are the same length as the word that is being searched
       for(int count = 0; count < word.length();count++ ) {
         
         
         if(count + x < board.length) {
        
           currentWord = currentWord + board[count + x][i];
         
         }
         
      }
       
       //Checks if the word has been found
       if((currentWord.toLowerCase().equals(word) || flipWord(currentWord.toLowerCase()).equals(word))  && (!currentWord.equals(word.toUpperCase()) && !flipWord(currentWord).equals(word.toUpperCase()))){
         
         startNum = i + x*columnLength;
         
         row = startNum / columnLength;
         column = startNum % columnLength;
         
         //Capitalize the word
         for(int y = 0; y < word.length(); y++){
    
           board[row + y][column] =   board[row + y][column].toUpperCase();
 
         }
         
         return board;
       }
     }
   }
   
   return board;
 }

 
   /*
  * findHorizontal
  * This method accepts a word search and a word to search for. It finds the first horizontal instance of a word
  * @param board a 2d array of the board
  * @param word a string of the word to search for
  * @return the word search with the found word capatailzed or the same board if it is not
  */
 public static String[][] findHorizontal(String[][]board, String word){
   
   String currentWord = "";
   int rowLength = board.length; 
   int columnLength= board[0].length;
   int row;
   int column;
   int startNum;
   

  //Loops through the columns
   for(int i = 0; i < board.length; i++) {
     
     //Loops through the rows
     for(int x = 0; x < board[0].length; x++){
      
       currentWord = "";
       
       //Loops through and creates all possible words that are the same length as the one being searched for
       for(int count = 0; count < word.length();count++ ) {
         
         if(count + x < board[i].length) {
         
           currentWord = currentWord + board[i][count +x];
         
         }
         
         
       }

       //Checks if the current word is equal to the word that is being searched for    
       if((currentWord.toLowerCase().equals(word) || flipWord(currentWord.toLowerCase()).equals(word))  && (!currentWord.equals(word.toUpperCase()) && !flipWord(currentWord).equals(word.toUpperCase()))){
         
         //Gets the position of the starting letter
         startNum = x + i* columnLength;
         row = startNum / columnLength ;
         column = startNum % columnLength;
        
        
        //Capatilzes the word
         for(int y = 0; y < word.length(); y++){
    
           board[row][column + y] =   board[row][column + y].toUpperCase();
 
         }

         
         return board;
         
       }
     }
   }
   
   return board;
 }
 


 
  /*
  * findForwardDiag
  * This method finds searches forward diagonals for a word
  * @param board a 2d array of the word search
  * @param word a string of the word to search for
  * @return the word search with the found word capitalized. If no word is found it will return the same board
  */
 public static String[][] findForwardDiag(String[][]board, String word){
  
   int counter = 0;
   int letterCounter = 0;
   int backwardsLetterCounter = word.length();
   String currentWord;
   

   for(int i = 0; i  < board.length -1; i++){
     
     for(int j = board[0].length -1; j > 0 ; j--){
        
        letterCounter = 0;
        backwardsLetterCounter = word.length();
        currentWord = "";


       for(int count = 0; count < board[0].length; count++){
         
         if((i + count > board.length -1) || (j - count < 0)){
           break;
         
         }

      
          //Checks if the letters are equal to each other and adds one to the count
          if(board[i + count][j - count].toLowerCase().equals(word.substring(letterCounter, letterCounter +1))){
           letterCounter++;

          }else if(board[i + count][j - count].toLowerCase().equals(word.substring(0,1))) {
            letterCounter = 1;
            
         }else{
           letterCounter = 0;
         }
         
         //Checks if the last letters are equal to each other and adds one to the count
        if(board[i + count][j - count].toLowerCase().equals(word.substring(backwardsLetterCounter -1, backwardsLetterCounter ))){
           backwardsLetterCounter--;

         }else if(board[i + count][j - count].toLowerCase().equals(word.substring(word.length()-1,word.length()))) {
            backwardsLetterCounter = word.length()-1;

         }else{
          backwardsLetterCounter = word.length();
         }
            

        //Checks if all letters are equal
         if((letterCounter == word.length()) || (backwardsLetterCounter == 0)){


          //Capitalize the word
           for(int z = 0; z < word.length(  ); z++){

             currentWord =  currentWord + board[i+ count - z][j-count + z];      
             board[i+ count - z][j-count + z] = board[i+count - z][j-count + z].toUpperCase(); 
            
           
           }

          //Returns the board if all letters arent capitalized
           if((!word.toUpperCase().equals(currentWord) )&& (!flipWord(word.toUpperCase()).equals(currentWord))){

            return board;
            
          }

          letterCounter = 0;
          backwardsLetterCounter = word.length();

         }  
       }

       counter++;
       if(counter > board[0].length- 1){
         break;

       }
     } 
   }
   
  return board;
 }
 
 
 
 
  /*
  * findBackwardDiag
  * This method finds searches backwards diagonals for a word
  * @param board a 2d array of the word search
  * @param word a string of the word to search for
  * @return the word search with the found word capitalized. If no word is found it will return the same board
  */
 public static String[][] findBackwardDiag(String[][] board, String word){

   int letterCounter = word.length();
   int counter = 0;
   int backwardsLetterCounter = 0;
   String currentWord;

  for(int i = board.length- 1; i  > 0; i--){
     
    for(int j = board[0].length- 1 ; j > 0 ; j--){
         
      letterCounter = word.length();
      backwardsLetterCounter = 0;
      currentWord = "";


      for(int count = 0; count < board.length; count++){


        //If the letters are out of the index break
        if((i - count < 0) || (j - count < 0)){

          break;
        }

        
        
        //If the letter is equal add one to the count
        if(board[i - count][j-count].toLowerCase().equals(word.substring(letterCounter - 1, letterCounter))){
          
      
          letterCounter--;

        }else if(board[i - count][j-count].toLowerCase().equals(word.substring(word.length() -1, word.length()))){

          letterCounter = word.length()-1;

        }else{
          letterCounter = word.length();
        }
        
        //If the letter is equal add one to the count
        if(board[i - count][j-count].toLowerCase().equals(word.substring(backwardsLetterCounter , backwardsLetterCounter + 1))){
          

          backwardsLetterCounter++;
          
        }else if(board[i - count][j-count].toLowerCase().equals(word.substring(0,1))){

          backwardsLetterCounter = 1;
          
        }else{
        
          backwardsLetterCounter = 0;
        }

        

        //Checks if all letters match
        if((letterCounter == 0) || (backwardsLetterCounter == word.length())){
                  
          //Capitalize the word 
          for(int z = 0; z < word.length(); z++){

            currentWord = currentWord + board[i - count + z][j-count + z];
            board[i - count + z][j-count + z] = board[i - count + z][j-count +z].toUpperCase();
           
           

          }


        //Returns the board if all letters are not capatalized
        if(!word.toUpperCase().equals(currentWord) && !flipWord(word.toUpperCase()).equals(currentWord)){

          return board;
          
        }  

        letterCounter = word.length();
        backwardsLetterCounter = 0;
        
      }
    
    
      }
    

            counter++;
            if(counter > board[0].length-1){
              break;
            }
      
      

      }        
    }
    return board;
 }

 
 /*
  * outputHTMLFile
  * This method accepts a completed word search and outputs a html file displaying it
  * @param board a 2d array of the completed word search
  * @param words an array of all the words found
  * @return
  */
 public static boolean outputHTMLFile(String[][] board, String[] words) throws Exception{


  File solvedSearch = new File("solvedwordsearch.html");
  PrintWriter fileOutput = new PrintWriter(solvedSearch);


  fileOutput.println("<!DOCTYPE html>");
  fileOutput.println("<html>");
  fileOutput.println("<head>");
  fileOutput.println("<title>Word Search</title>");
  fileOutput.println("</head>");
  fileOutput.println("<body>");
  fileOutput.println("<table>");

  for(int count = 0; count < board.length; count++){
        fileOutput.println("<tr>");
        for(int i = 0;i < board[count].length;i++){
          
          
        //Colour the found words as blue
         if(board[count][i].equals(board[count][i].toUpperCase())){

           fileOutput.print("<th = style =\"color:blue\"> " +  board[count][i] +"</th>");

         }else{
         fileOutput.print("<th>" + board[count][i] + "</th>");
         }
            
        }
        fileOutput.println("</tr>");
        
        
       }
  

  fileOutput.println("</table>");

  //Display the words that were searched for
  for(int i = 0; i < words.length; i++){
    
    if(words[i] != null){
    fileOutput.println("<p>" + words[i] + "</p>"); 
}
  }
  
  fileOutput.println("</body>");
  fileOutput.println("</html>");

  fileOutput.close();

  return true;
 }
 
 
  /*
  * flipWord
  * This method accepts a word and returns the backwards version of it
  * @param word the string that is going to be flipped
  * @return the flipped version of the string
  */
 public static String flipWord(String word){
   
   String flippedWord = "";
   
   for(int i = word.length(); i > 0; i--){
     flippedWord = flippedWord + word.substring(i - 1, i);
   }
   return flippedWord; 
 }
}