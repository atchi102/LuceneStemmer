

import org.tartarus.snowball.ext.EnglishStemmer;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;


public class Stemmer{
  private static EnglishStemmer stemmer;

  public static void main(String[] args) throws IOException
  {
    stemmer = new EnglishStemmer();
    //TODO add directory of text files here
    parseDir("/Users/t-abatch/Documents/plzwork");
  }

  public static void parseDir(String path) throws IOException {
    //list files in current directory
    File currentDir = new File(path);
    File[] contents = currentDir.listFiles();
    int numFilesInDir = contents.length;
    String fileContents;

    //iterate through files
    for(int k=0; k<numFilesInDir; ++k)
    {
      fileContents = " ";
      String contentName = contents[k].getAbsolutePath();

      if(contentName.endsWith("/.DS_Store"))
        continue;

      Path file = new File(contentName).toPath();

      //if the file is a directory call the parseDir method on it and skip to next file
      if(Files.isDirectory(file))
      {
        parseDir(contentName);
        continue;
      }
      else if(contentName.endsWith(".txt"))
      {
        fileContents = stemWords(readFile(contentName));
        //TODO: Add the directory of the output here (NOTE!!! this should be outside of the directory of the text files you are parsing through)
        File newFile = new File("/Users/t-abatch/Documents/output/" + contentName.substring(contentName.lastIndexOf('/'),contentName.indexOf(".txt")));
        PrintWriter writer = new PrintWriter(newFile);
        writer.println(stemWords(readFile(contentName)));
        writer.close();
      }



    }
  }

  public static String readFile(String path) throws IOException
  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, Charset.defaultCharset());
  }

  public static String stemWords(String words)
  {
    StringBuilder phrase = new StringBuilder();
    for(String word: words.split(" ")){
      stemmer.setCurrent(word);
      stemmer.stem();
      phrase.append(stemmer.getCurrent()).append(' ');
  }

    return phrase.toString();
  }



}
