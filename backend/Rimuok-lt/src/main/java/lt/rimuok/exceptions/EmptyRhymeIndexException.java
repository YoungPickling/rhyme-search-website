package lt.rimuok.exceptions;

import lombok.Data;

/**
 * EmptyRhymeIndexException is a custom exception class (extends NullPointerException to dispose of try-catch blocks).
 * It is thrown when an attempt is made to access a rhyme index that has no records.
 */

@Data
public class EmptyRhymeIndexException extends NullPointerException {
    private String word;

    public EmptyRhymeIndexException(String message, String word) {
        super(message);
        this.word = word.toLowerCase();
    }
}
