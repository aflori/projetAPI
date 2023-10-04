package fr.ecolnum.projectapi.DTO;

public class ResultImportListDto {
    private Iterable<CandidateDto> imported;
    private Iterable<CandidateDto> duplicate;
    private Iterable<CandidateDto> photoMissing;

    public ResultImportListDto(Iterable<CandidateDto> imported, Iterable<CandidateDto> duplicate, Iterable<CandidateDto> photoMissing) {
        this.imported = imported;
        this.duplicate = duplicate;
        this.photoMissing = photoMissing;
    }

    public ResultImportListDto() {
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
