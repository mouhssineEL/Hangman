
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Operation {
         static ArrayList<String> wordsList = new ArrayList<String>( );
         private  static String source = "src/words.txt";

    public void addWordToFile(String word) throws IOException {
        FileWriter fw = new FileWriter(source, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(word);
        bw.write(",");
        bw.newLine();
        bw.close();
    }
     static public String  choiceRandomWord(){
        //choice a random word from file in java?
        final List<String> words = readingFilesInputStream();
        System.out.println(" list of words  " + words);
        Random yourRandom = new Random();
        String word = words.get(yourRandom.nextInt(words.size()));//    return lines.get(rand.nextInt(lines.size()));
        System.out.println("the word choised randomly :"+word);
        return word;
    }

    public  static List readingFilesInputStream(){
		//System.out.println("Reading using FileInputStream");
		try {
			String ligne;
			BufferedReader fichier = new BufferedReader(new InputStreamReader(new FileInputStream(source), "UTF-8"));
			while ((ligne = fichier.readLine()) != null) {
				    //System.out.println(ligne);
			    String[] words = ligne.split(",");
			    for (String word : words) {
			    	word = word.trim();
			    	wordsList.add(word);
			    	//System.out.println(word);
			    }
			    
			}
            return wordsList;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        return wordsList;
	}


}
