package sn.martial.avis.entite;

public enum StatusAvis {
    EN_ATTENTE("En attente"),
    APPROUVE("Approuvé"),
    REJETE("Rejeté");

    private final String libelle;

    StatusAvis(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
