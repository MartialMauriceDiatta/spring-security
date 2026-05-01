package sn.martial.avis.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.martial.avis.entite.Avis;
import sn.martial.avis.service.AvisService;

/**
 * Contrôleur REST pour la gestion des avis
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/avis")
@RestController
public class AvisController {

    private final AvisService avisService;

    /**
     * Crée un nouvel avis
     *
     * @param request Détails de l'avis à créer (validé)
     * @return Réponse 201 avec l'avis créé
     */
    @PostMapping
    public ResponseEntity<Avis> creer(@RequestBody Avis request) {
        log.info("Réception d'une demande de création d'avis");
        Avis avisResponse = avisService.creer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(avisResponse);
    }
}
