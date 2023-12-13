package lt.rimuok;

public class Utils {
    public static int[][] convertStringTo2DArray(String stressString) {
        try {
            System.out.println("Original stressString: " + stressString);

            // Remove leading and trailing braces
            stressString = stressString.substring(2, stressString.length() - 2);
            System.out.println("Trimmed stressString: " + stressString);

            // Split by '},{'
            String[] rows = stressString.split("\\},\\{");
            int numRows = rows.length;
            int[][] stressArray = new int[numRows][];

            for (int i = 0; i < numRows; i++) {
                // Add braces back to the rows
                String rowWithBraces = "{" + rows[i] + "}";
                System.out.println("Row with braces: " + rowWithBraces);

                // Replace any non-digit characters before splitting
                rowWithBraces = rowWithBraces.replaceAll("[^0-9,]", "");
                System.out.println("Cleaned row: " + rowWithBraces);

                String[] cols = rowWithBraces.split(",");
                int numCols = cols.length;
                stressArray[i] = new int[numCols];

                for (int j = 0; j < numCols; j++) {
                    stressArray[i][j] = Integer.parseInt(cols[j]);
                }
            }

            return stressArray;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer: " + e.getMessage());
            e.printStackTrace();
            throw e; // rethrow the exception after printing details
        }
    }

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

}
