import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
            
            File file = new File(this.inputFileName);
            
            Scanner scanner = new Scanner(file);
            
            
            
            // Use lines to read in all sentences from the input file
            
            ArrayList<String> lines = new ArrayList<String>();
            
            ArrayList<String> sentences = new ArrayList<String>();
            
            
            
            while (scanner.hasNextLine()) {
                
                //StringTokenizer tokens = new StringTokenizer(scanner.nextLine(),this.delimiters);
                
                //System.out.println("Total tokens:" + tokens.countTokens());]
                
                String sentence = scanner.nextLine();
                
                sentence = sentence.toLowerCase().trim();
                
                //System.out.println(sentence);
                
                sentences.add(sentence);
                
                
                
            }
            
            scanner.close();
            
            System.out.println("SIZE of sentences " + sentences.size());
            
            
            
            List <String> stopWordsArrayList = Arrays.asList(this.stopWordsArray);
            
            
            
            // Call getIndexes()
            
            Integer[] indicies = getIndexes();
            
            ArrayList<String> words = new ArrayList<String>();
            
            for(Integer index: indicies){
                
                //System.out.println(index);
                
                String sentence = sentences.get(index);
                
                StringTokenizer stringsOfToken = new StringTokenizer(sentence,this.delimiters);
                
                //System.out.println("String for this sentence: " + stringsOfToken.toString());
                
                while(stringsOfToken.hasMoreTokens()){
                    
                    String tokenString = stringsOfToken.nextToken();
                    
                    if(! stopWordsArrayList.contains(tokenString)){
                        
                        words.add(tokenString);
                        
                    }
                    
                    
                    
                }
                
                
                
            }
            
            System.out.println("SIZE of words " + words.size());
            
            
            
            
            
            
            
            // Now we just want a set of unique words from the words list
            
            
            
            Set<String> uniqueWords = new HashSet<String>(words);
            
            System.out.println("SIZE of uniqueWords " + uniqueWords.size());
            
            
            
            
            
            Map<Integer, String> treemap = new TreeMap<Integer, String>(Collections.reverseOrder());
            
            
            
            for (String unique : uniqueWords) {
                
                int freq = Collections.frequency(words, unique);
                
                System.out.println("uniqueWord: " + unique + " Freq: " + freq);
                
                treemap.put(freq,unique);
                
            }
            
            
            
            
            
            Set set = treemap.entrySet();
            
            Iterator i = set.iterator();
            
            Integer j = 0;
            
            // Display elements
            
            while(i.hasNext()) {
                
                Map.Entry mine = (Map.Entry)i.next();
                
                //System.out.print(mine.getKey() + ": ");
                
                //System.out.println(mine.getValue());
                
                
                
                // Since we are already iterating reverseOrder, Save off the first 20 only 
                
                if(j<=19){
                    
                    ret[j] = mine.getValue().toString() + " " + mine.getKey().toString();
                    
                    //ret[j] = mine.getValue().toString(); 
                    
                    j++;
                    
                } else {
                    
                    
                    
                    break;
                    
                }
                
                
                
            }
            
        

        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
