package sn.martial.avis.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.martial.avis.entite.Utilisateur;
import sn.martial.avis.service.UtilisateurService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("utilisateur")
@RestController
public class UtilisateurController {
    UtilisateurService utilisateurService;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATEUR_READ', 'MANAGER_READ')")
    @GetMapping
    public List<Utilisateur> liste() {
        return this.utilisateurService.liste();
    }
}