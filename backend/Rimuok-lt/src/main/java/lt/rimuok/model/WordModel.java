package lt.rimuok.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordModel {
    private String wo; // word
    private int sy; // syllables
    private int sa; // stress at
    private int st; // st
    private int ps; // part of speech
}
