package sn.martial.avis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.martial.avis.entite.Avis;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
}
