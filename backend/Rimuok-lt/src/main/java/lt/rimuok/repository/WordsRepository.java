package lt.rimuok.repository;

import lt.rimuok.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<Words, Short> {

}