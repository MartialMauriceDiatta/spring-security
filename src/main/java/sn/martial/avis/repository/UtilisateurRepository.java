package sn.martial.avis.repository;

import org.springframework.data.repository.CrudRepository;
import sn.martial.avis.entite.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long> {
    Optional<Utilisateur> findByEmail(String email);
}
