import java.util.*;
import java.io.*;

public class Converter {

  public static void main(String[] args) {
    Scanner sc = new Scanner("");

    //Checks argument.
    if(args.length != 1) {
      System.out.println("Please input at least one text file.");
      System.exit(0);
    }

    try {
      sc = new Scanner(new File(args[0])); //Opens the file to read it
    } catch(Exception e) {
      System.out.println("Could not correctly load the file.");
      System.exit(0);
    }

    int[] asciiArray = convertToASCII(sc);

    String fileOutput = generateBrainfuck(asciiArray);

    try {
      FileWriter fw = new FileWriter(new File("output.txt"));
      fw.write(fileOutput);
      fw.close();
    } catch (Exception e) {
      System.out.println("Could not create the output file successfully.");
      System.exit(0);
    }

    System.out.println("File converted successfully.");

  }

  //Method who ouputs an array of integers corresponding to the ascii value of each character
  public static int[] convertToASCII(Scanner sc) {

    ArrayList<Integer> asciiArray = new ArrayList<>();

    while(sc.hasNext()) {
      String line = sc.nextLine();
      for(int i=0; i<line.length();i++) {
        asciiArray.add((int)line.charAt(i));
      }

      asciiArray.add(10); //ascii value for a new line
    }

    //Converts the List to an Array
    int[] output = new int[ asciiArray.size() ];
    for(int i=0; i<asciiArray.size(); i++)
      output[i] = asciiArray.get(i);

    return output;
  }

  public static String generateBrainfuck(int[] asciiArray) {
    String output = "";
    //Step one, add to the cells the closest tenth of the ascii value for each character (112 --> 110, 39 --> 40...)
    output += "++++++++++[";
    for(int i=0; i<asciiArray.length; i++) {
      output += ">";
      int nbPlus = Math.round(asciiArray[i]/10f);
      while(nbPlus>0){output += "+"; nbPlus--;}
    }
    for(int i=0; i<asciiArray.length; i++) {output += "<";}
    output += "-]";

    //Step two, add or substract depending on the value needed (from 110 to 113 --> +++)
    for(int i=0; i<asciiArray.length; i++) {
      int neededValue = asciiArray[i];
      int ogValue = 10*(Math.round(asciiArray[i]/10f));
      int steps = neededValue - ogValue;
      char sign = ' ';
      if(steps>0) {
        sign = '+';
      } else {
        sign = '-';
      }
      output += ">";
      for(int j=0; j<Math.abs(steps); j++) {
        output += sign;
      }

      output += ".";
    }

    return output;
  }

}
