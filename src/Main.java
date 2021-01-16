import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("***** Welcome To My Dictionary Program *****");
        startApp();
    }

    private static void startApp() {
        System.out.println("\nPlease Select Your Operation : ");
        System.out.println("1-Add New Vocabulary");
        System.out.println("2-Delete Vocabulary ");
        System.out.println("3-Edit Your Vocabulary ");
        System.out.println("4-Translate Your Vocabulary ");
        System.out.println("5-Exit App");
        System.out.print("Your App : ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();
        if (!choice.trim().equals("") && choice.trim().matches("\\d")) {
            int operation = Integer.parseInt(choice);
            switch (operation) {
                case 1:
                    addVocabulary(scanner);
                    break;
                case 2:
                    deleteWord(scanner);
                    break;
                case 3:
                    editWord(scanner);
                    break;
                case 4:
                    translateVocabulary(scanner);
                    break;
                case 5:
                    System.out.println("Good Bye ......................");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Sorry your operation is wrong");
                    System.out.println("Please Try Again ");
                    startApp();
            }
        } else {
            System.out.println("Your Operation is wrong ===> " + choice);
            System.out.println("Please Try Again ");
            startApp();
        }
    }

    private static void editWord(Scanner scanner) {
        System.out.print("Enter your word : ");
        String word = scanner.next();
        Vocabulary vocabulary = DictionaryApi.getVocabulary(word);
        if (vocabulary != null) {
            System.out.println(String.format("This is your word ===>  %s ", vocabulary.getWord()));
            System.out.print("Please Enter your updated word :  ");
            String newWord = scanner.next();
            boolean isUpdated = DictionaryApi.isUpdated(word, newWord);
            if (isUpdated) {
                System.out.println("Your word is Updated ...........");
            } else {
                System.out.println("Sorry can not update Word ..............");
            }
        } else {
            System.out.println("Sorry This word does not exist ..........");
        }
        startApp();
    }

    private static void deleteWord(Scanner scanner) {
        System.out.print("Enter your word : ");
        String word = scanner.next();
        boolean isDeleted = DictionaryApi.deleteWord(word);
        if (isDeleted) {
            System.out.println("Your Word is Deleted");
        } else {
            System.out.println("Sorry This word does not exist ..........");
        }
        startApp();
    }

    private static void translateVocabulary(Scanner scanner) {
        System.out.print("Enter your word : ");
        String word = scanner.next();
        List<Vocabulary> translateList = DictionaryApi.translate(word);
        if (translateList.size() > 0) {
            System.out.println("Translate your word is : ");
            for (Vocabulary vocabulary : translateList) {
                System.out.println(String.format("%s ====> %s", vocabulary.getWord(), vocabulary.getTranslation()));
            }
        } else {
            System.out.println("Sorry This word does not exist ..............");
        }
        startApp();
    }

    private static void addVocabulary(Scanner scanner) {
        System.out.print("Enter your vocabulary : ");
        String word = scanner.next();
        boolean isAdded = DictionaryApi.addWord(word);
        if (isAdded) {
            System.out.println("Your Word is Added ");
        } else {
            System.out.println("Sorry Can not Add word to vocabulary ");
        }
        startApp();
    }
}
