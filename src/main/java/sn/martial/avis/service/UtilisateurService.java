package sn.martial.avis.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sn.martial.avis.entite.Role;
import sn.martial.avis.entite.TypeDeRole;
import sn.martial.avis.entite.Utilisateur;
import sn.martial.avis.entite.Validation;
import sn.martial.avis.repository.RoleRepository;
import sn.martial.avis.repository.UtilisateurRepository;
import sn.martial.avis.repository.ValidationRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService {
    private UtilisateurRepository utilisateurRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ValidationService validationService;
    private ValidationRepository validationRepository;

    public Utilisateur inscrireUtilisateur(Utilisateur utilisateur) {

        if (!utilisateur.getEmail().contains("@") || !utilisateur.getEmail().contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }

        Optional<Utilisateur> utilisateurOptional =
                utilisateurRepository.findByEmail(utilisateur.getEmail());

        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Votre email est déjà utilisé");
        }

        String encodedPassword =
                bCryptPasswordEncoder.encode(utilisateur.getMotDePasse());

        utilisateur.setMotDePasse(encodedPassword);

        Role roleUtilisateur = (Role) roleRepository.findByLibelle(TypeDeRole.USER)
                .orElseThrow(() -> new RuntimeException("Role USER introuvable"));

        utilisateur.setRole(roleUtilisateur);
        utilisateur.setActif(false);

        utilisateur = utilisateurRepository.save(utilisateur);

        validationService.enregistrerValidation(utilisateur);

        return utilisateur;
    }

    public Utilisateur activation(Map<String, String> activation) {

        Validation validation = this.validationService
                .lireEnFonctionDuCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }

        Utilisateur utilisateurActiver = validation.getUtilisateur();

        utilisateurActiver.setActif(true);

        // 🔥 Marquer comme activé
        validation.setActivation(Instant.now());

        this.utilisateurRepository.save(utilisateurActiver);
        this.validationRepository.save(validation);

        return utilisateurActiver;
    }

    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }

    public List<Utilisateur> liste() {
        final Iterable<Utilisateur> utilisateurIterable = this.utilisateurRepository.findAll();
        List<Utilisateur> utilisateurs = new ArrayList();
        for (Utilisateur utilisateur : utilisateurIterable) {
            utilisateurs.add(utilisateur);
        }
        return utilisateurs;
    }
}
