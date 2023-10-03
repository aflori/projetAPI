package fr.ecolnum.projectapi.DTO;

public class resultImportListDto {
    private Iterable<CandidateDto> imported;
    private Iterable<CandidateDto> duplicate;
    private Iterable<CandidateDto> photoMissing;

    public resultImportListDto(Iterable<CandidateDto> imported, Iterable<CandidateDto> duplicate, Iterable<CandidateDto> photoMissing) {
        this.imported = imported;
        this.duplicate = duplicate;
        this.photoMissing = photoMissing;
    }

    public resultImportListDto() {
    }

    public Iterable<CandidateDto> getImported() {
        return imported;
    }

    public void setImported(Iterable<CandidateDto> imported) {
        this.imported = imported;
    }

    public Iterable<CandidateDto> getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Iterable<CandidateDto> duplicate) {
        this.duplicate = duplicate;
    }

    public Iterable<CandidateDto> getPhotoMissing() {
        return photoMissing;
    }

    public void setPhotoMissing(Iterable<CandidateDto> photoMissing) {
        this.photoMissing = photoMissing;
    }
}
