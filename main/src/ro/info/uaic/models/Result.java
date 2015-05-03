package ro.info.uaic.models;

/**
 * Created by lotus on 03.05.2015.
 */
public class Result
{
    private int candidateId;
    private float admissionMark;
    private Status status;
    private String remarks;

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public float getAdmissionMark() {
        return admissionMark;
    }

    public void setAdmissionMark(float admissionMark) {
        this.admissionMark = admissionMark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
