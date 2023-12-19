package lt.rimuok;

import lt.rimuok.model.WordModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static final String[] VOWEL_SET = {"a", "ą", "ai", "au", "iai", "iau", "ia", "ią", "e", "ė", "ę", "ei", "eu", "ie", "i", "į", "y","o", "oi", "ou", "uo", "io", "iuo", "u", "ų", "ū", "ui", "iu", "ių", "iū", "iui"};

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

    public static String[] extractVowelGroups(final String word) {
        List<String> res = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        boolean vowelGroupStart = false;

        for (int i = 0; i < word.length(); i++) {
            if("aeiyouąęėįųūAEIYOUĄĘĖĮŲŪ".indexOf(word.charAt(i)) != -1) {
                vowelGroupStart = true;
                builder.append(word.charAt(i));
                if(i == word.length() - 1) {
                    vowelGroupStart = false;
                    res.add(builder.toString());
                    builder.setLength(0);
                }
            } else {
                if (vowelGroupStart) {
                    vowelGroupStart = false;
                    res.add(builder.toString());
                    builder.setLength(0);
                }
            }
        }

        for (int i = 0; i < res.size(); i++) {
            final int indexCopy = i;
            if(Arrays.stream(VOWEL_SET).noneMatch( x -> x.equals(res.get(indexCopy)) )) {
                for (int j = 1; j <= res.get(i).length(); j++) {
                    final int jIndexCopy = j;
                    if(Arrays.stream(VOWEL_SET).noneMatch( x -> x.equals(res.get(indexCopy).substring(0, jIndexCopy)) )) {
                        String first = res.get(indexCopy).substring(0, jIndexCopy - 1);
                        String second = res.get(indexCopy).substring(jIndexCopy - 1);
                        res.set(i, second);
                        res.add(i, first);
                        break;
                    }
                }
            }
        }

        return res.toArray(new String[0]);
    }

    public static List<String> getAllRhymeIndexes(String[] vowels) {
        if (vowels.length == 1 && vowels[0].length() == 1) {
            List<String> altRes = new ArrayList<>();
            altRes.add(defaultIndexing(vowels[0]).toUpperCase());
            return altRes;
        }

        List<String> res = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vowels.length; i++) {

            if(vowels[i].length() == 1) {
                builder.append(defaultIndexing(vowels[i]).toUpperCase());

                for (int j = i + 1; j < vowels.length ; j++)
                    builder.append(defaultIndexing(vowels[j]));

                res.add(builder.toString());
                builder.setLength(0);
            } else {
                String[] group = wideIndexing(vowels[i]);

                for (int j = i + 1; j < vowels.length ; j++)
                    builder.append(defaultIndexing(vowels[j]));

                for (int j = 0; j < group.length ; j++)
                    res.add(group[j] + builder);

                builder.setLength(0);
            }

        }
        return res;
    }

    public static String defaultIndexing(final String word) {
        switch (word) {
            case "a", "ą", "ai", "au", "iai", "iau", "ia", "ią":
                return ("a");
            case "e", "ė", "ę", "ei", "eu", "ie":
                return("e");
            case "i", "į", "y":
                return("i");
            case "o", "oi", "ou", "uo", "io", "iuo":
                return("o");
            //case "u", "ų", "ū", "ui", "iu", "ių", "iū", "iui":
            default:
                return("u");
        }
    }

    public static String[] wideIndexing(final String word) {
        switch (word) {
            case "ai" :
                return new String[]{"Ai","I"};
            case "au" :
                return new String[]{"Au","U"};
            case "ei" :
                return new String[]{"Ei","I"};
            case "eu" :
                return new String[]{"Eu","U"};
            case "ia", "ią":
                return new String[]{"Ia","A"};
            case "ie":
                return new String[]{"Ie","E"};
            case "io":
                return new String[]{"Io","O"};
            case "oi" :
                return new String[]{"Oi","I"};
            case "ou" :
                return new String[]{"Ou","U"};
            case "uo" :
                return new String[]{"Uo","O"};
            case "ui":
                return new String[]{"Ui","I"};
            case "iu", "ių", "iū":
                return new String[]{"Iu","U"};
            case "iui":
                return new String[]{"Iu","Ui","I"};
            case "iuo":
                return new String[]{"Io","Uo","O"};
            case "iai":
                return new String[]{"Ia","Ai","I"};
            case "iau":
                return new String[]{"Ia","Au","U"};
        }
        return new String[]{};
    }

    public static int[] getVowelIndexes(final String word) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < word.length(); i++)
            if("aeiyouąęėįųū".contains("" + word.charAt(i)))
                result.add(i);

        return result.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

}
