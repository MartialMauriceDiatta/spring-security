package sn.martial.avis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.martial.avis.entite.Role;
import sn.martial.avis.entite.TypeDeRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Object> findByLibelle(TypeDeRole typeDeRole);
}
