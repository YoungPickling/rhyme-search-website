package lt.rimuok.repository;

import lt.rimuok.entity.PartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartOfSpeechRepository extends JpaRepository<PartOfSpeech, Short> {

}