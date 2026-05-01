package sn.martial.avis.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sn.martial.avis.entite.Utilisateur;
import sn.martial.avis.entite.Validation;
import sn.martial.avis.repository.ValidationRepository;


import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;


@Service
@AllArgsConstructor
public class ValidationService {
    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrerValidation(Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);

//        Instant expiration = Instant.now();
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String code = String.format("%06d", randomNumber);
        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation lireEnFonctionDuCode(String  code) {
       return this.validationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    };
}
