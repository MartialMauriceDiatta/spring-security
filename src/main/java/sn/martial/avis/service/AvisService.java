package sn.martial.avis.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.martial.avis.entite.Avis;
import sn.martial.avis.entite.StatusAvis;
import sn.martial.avis.entite.Utilisateur;
import sn.martial.avis.repository.AvisRepository;
import sn.martial.avis.repository.UtilisateurRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service pour la gestion des avis utilisateurs
 */
@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class AvisService {

    private final AvisRepository avisRepository;
    private final UtilisateurRepository utilisateurRepository;

    public Avis creer(Avis request) {

        if (request.getUtilisateur() == null || request.getUtilisateur().getId() == null) {
            throw new IllegalArgumentException("L'utilisateur est obligatoire");
        }

        Long utilisateurId = request.getUtilisateur().getId();

        log.info("Création d'un nouvel avis pour l'utilisateur ID: {}", utilisateurId);

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> {
                    log.error("Utilisateur non trouvé: {}", utilisateurId);
                    return new NoSuchElementException(
                            "Utilisateur avec l'ID " + utilisateurId + " n'existe pas"
                    );
                });

        Avis avis = Avis.builder()
                .message(request.getMessage())
                .status(request.getStatus() != null ? request.getStatus() : StatusAvis.EN_ATTENTE)
                .utilisateur(utilisateur)
                .build();

        Avis avisSauvegarde = avisRepository.save(avis);

        log.info("Avis créé avec succès avec l'ID: {}", avisSauvegarde.getId());

        return avisSauvegarde;
    }

    public List<Avis> lister() {
        log.info("Récupération de tous les avis");
        return avisRepository.findAll();
    }
}