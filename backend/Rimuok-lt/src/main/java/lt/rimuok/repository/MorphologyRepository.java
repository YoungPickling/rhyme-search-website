package lt.rimuok.repository;

import lt.rimuok.entity.Morphology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MorphologyRepository extends JpaRepository<Morphology, Short> {

}