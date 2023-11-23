package lt.rimuok.repository;

import lt.rimuok.dto.JoinedTable;
import lt.rimuok.entity.JunctionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JunctionTableRepository extends JpaRepository<JunctionTable, Integer> {
    @Query("SELECT new lt.rimuok.dto.JoinedTable(z.zodziai.zodis AS word, " +
            "levenshtein(z.zodziai.zodis, :search) AS distance, " +
            "z.kalbosDalys.kalbosDaliesId AS kdId, " +
            "z.kircioVieta AS stress_at, " +
            "z.kircioZenklas AS stress_type " +
            "FROM JunctionTable z) " +
            "JOIN zodziai w " +
//            "JOIN z.kalbosDalys k " +
//            "JOIN z.morfologija m " +
            "ORDER BY distance" +
            "LIMIT 2000")
    List<JoinedTable> customQuery(@Param("search") String searchWord);
}
