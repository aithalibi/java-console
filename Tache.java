import java.time.LocalDateTime;

public class Tache {
    private String description;
    private boolean estTerminee;
    private LocalDateTime dateCreation;

    public Tache(String description) {
        this.description = description;
        this.estTerminee = false;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters et Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEstTerminee() {
        return estTerminee;
    }

    public void setEstTerminee(boolean estTerminee) {
        this.estTerminee = estTerminee;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        String statut = estTerminee ? "[✓]" : "[ ]";
        return String.format("%s %s (créée le %s)", 
            statut, 
            description, 
            dateCreation.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
} 