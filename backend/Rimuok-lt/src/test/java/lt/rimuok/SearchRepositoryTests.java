package lt.rimuok;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lt.rimuok.model.WordModel;
import lt.rimuok.repository.SearchRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJdbcTest
//@NoArgsConstructor
public class SearchRepositoryTests {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private SearchRepository searchRepository;

//    @Autowired
//    public SearchRepositoryTests(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

//    @Test
//    public void getRhymeIndexTest() {
//        // Arrange
//        final String word = "labas";
//
//        // Act
//        final String result = searchRepository.getRhymeIndex(word);
//
//        // Assert
//        assertTrue(result.equals(word));
//    }

    // TODO tests
    @Test
    public void testGetRhymeIndex() {
        // Given
        String word = "knyga";
        String expectedRhymeIndex = "Ia";

        // When
        SearchRepository searchRepository = new SearchRepository();
        String actualRhymeIndex = searchRepository.getRhymeIndex(word);

        // Then
        assertTrue(expectedRhymeIndex.equals(actualRhymeIndex));
    }

//    @Test
//    public void testFindWordsByRhymeIndex() {
//        // Given
//        String[] rhymeIndexes = {"a"};
//        List<WordModel> expectedWords = Arrays.asList(
//                new WordModel("knyga", 2),
//                new WordModel("mama", 2)
//        );
//
//        // When
//        SearchRepository searchRepository = new SearchRepository();
//        List<WordModel> actualWords = searchRepository.findWordsByRhymeIndex(rhymeIndexes);
//
//        // Then
//        assertEquals(expectedWords.size(), actualWords.size());
//        for (int i = 0; i < expectedWords.size(); i++) {
//            assertEquals(expectedWords.get(i).getWord(), actualWords.get(i).getWord());
//            assertEquals(expectedWords.get(i).getSyllable(), actualWords.get(i).getSyllable());
//        }
//    }
}
