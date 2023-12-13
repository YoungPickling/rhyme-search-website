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
}
