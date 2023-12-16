package lt.rimuok;

import lt.rimuok.model.WordModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static String getEnding(final String word) {
        // Check if the word is not empty
        if (word != null && !word.isEmpty()) {
            // Find the index of the last vowel
            int lastVowelIndex = findLastVowelIndex(word);

            // Find the index of the last consonant
            int lastConsonantIndex = findLastConsonantIndex(word);

            // Determine the starting index for the substring
            int startIndex;
            if (lastVowelIndex > lastConsonantIndex) {
                // If the last vowel comes after the last consonant, start from the last vowel
                startIndex = lastVowelIndex;
            } else {
                // Otherwise, start from the letter after the last consonant
                startIndex = lastConsonantIndex + 1;
            }

            // Return the substring from the calculated starting index to the end of the word
            return word.substring(startIndex);
        }

        // Return an empty string for invalid input
        return "";
    }

    private static boolean isVowel(final char c) {
        // Check if the character is a vowel
        return "aeiyouąęėįųūAEIYOUĄĘĖĮŲŪ".indexOf(c) != -1;
    }

    private static int findLastVowelIndex(final String word) {
        // Find the index of the last vowel in the word
        for (int i = word.length() - 1; i >= 0; i--) {
            if (isVowel(word.charAt(i))) {
                return i;
            }
        }
        return -1; // No vowel found
    }

    private static int findLastConsonantIndex(final String word) {
        // Find the index of the last consonant in the word
        for (int i = word.length() - 1; i >= 0; i--) {
            if (!isVowel(word.charAt(i))) {
                return i;
            }
        }
        return -1; // No consonant found
    }

    public static int findLastCapital(final String rhymeIndex) {
        // Find the index of the last vowel in the word
        for (int i = rhymeIndex.length() - 1; i >= 0; i--) {
            if (Character.isUpperCase(rhymeIndex.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static List<List<WordModel>> groupBySyllable(final List<WordModel> wordModels) {
        // Group WordModel instances by syllable using Java streams
        Map<Integer, List<WordModel>> groupedBySyllable = wordModels.stream()
                .collect(Collectors.groupingBy(WordModel::getSyllable));

        // Convert the map values (lists) to a list of lists
        return groupedBySyllable.values().stream().collect(Collectors.toList());
    }

}
