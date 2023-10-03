package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Candidate;

public class OptionnalCandidateDto extends CandidateDto {
    private boolean isDuplicate;

    public OptionnalCandidateDto() {
        this.isDuplicate = true;
    }

    public OptionnalCandidateDto(Candidate candidate) {
        super(candidate);
        this.isDuplicate = false;
    }


    public OptionnalCandidateDto(String firstName, String lastName, boolean isDuplicate) {
        super(firstName, lastName);
        this.isDuplicate = true;
    }
    public OptionnalCandidateDto(String firstName, String lastName, String photoUri, boolean isDuplicate) {
        super(firstName, lastName);
        this.setPhotoName(photoUri);
        this.isDuplicate = isDuplicate;
    }


    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }
}
