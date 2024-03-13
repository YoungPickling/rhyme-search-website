package lt.rimuok.repository;

import jakarta.activation.DataSource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lt.rimuok.model.WordModel;
import lt.rimuok.repository.SearchRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class SearchRepositoryTests {
    @Autowired
    public SearchRepository searchRepository;

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
//    @Test
//    void checkForNullReference_JdbcTemplate() {
//        assertNotNull(jdbcTemplate);
//    }

    @Test
    public void checkForNullReference_SearchRepository() {
        assertNotNull(searchRepository);
    }

    @Test
    public void testGetRhymeIndex() {
        // Given
        String word = "knyga";
        String expectedRhymeIndex = "Ia";

        // When
        String actualRhymeIndex = searchRepository.getRhymeIndex(word);

        // Then
        assertTrue(
                expectedRhymeIndex.equals(actualRhymeIndex),
                "Rhyme index result did not match with the expected one"
        );
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
