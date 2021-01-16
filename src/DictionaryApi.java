import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class DictionaryApi {
    private static final Map<String, Vocabulary> dictionary = new ConcurrentHashMap<String, Vocabulary>();

    public static boolean addWord(String word) {

        if (dictionary.containsKey(word)) {
            return false;
        } else {
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setWord(word);
            String response = translateWord(word);
            if (response != null) {
                vocabulary.setTranslation(response);
                dictionary.put(word, vocabulary);
                return true;
            } else {
                return false;
            }
        }
    }

    public static List<Vocabulary> translate(String word) {
        List<Vocabulary> translates = new ArrayList<>();
        dictionary.forEach((key, value) -> {
            if (key.contains(word)) {
                translates.add(dictionary.get(key));
            }
        });
        return translates;
    }

    public static Vocabulary getVocabulary(String word) {
        return dictionary.getOrDefault(word, null);
    }

    public static boolean isUpdated(String oldWord, String newWord) {
        if (dictionary.containsKey(oldWord)) {
            Vocabulary vocabulary = dictionary.get(oldWord);
            String response = translateWord(newWord);
            if (response != null) {
                vocabulary.setWord(newWord);
                vocabulary.setTranslation(response);
                dictionary.remove(oldWord);
                dictionary.put(newWord, vocabulary);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean deleteWord(String word) {
        if (dictionary.containsKey(word)) {
            dictionary.remove(word);
            return true;
        } else {
            return false;
        }
    }

    private static String translateWord(String word) {

        CompletableFuture<String> future = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            try {
                String response = Translator.translate("en", "fa", word);
                future.complete(response);
            } catch (Exception ex) {
                System.err.println("Error =====> " + ex.getMessage());
                System.err.println("Sorry Can not Connect to internet ..............");
            }
        });
        try {
            return future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
