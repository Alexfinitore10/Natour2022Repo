package com.INGSW.NaTour.Model.DTO;

public class EmailDTO {

    private String subject;
    private String body;
    private Long utenteid;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUtenteid() {
        return utenteid;
    }

    public void setUtenteid(Long utenteid) {
        this.utenteid = utenteid;
    }

    public EmailDTO(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public EmailDTO(String subject, String body, Long utenteid) {
        this.subject = subject;
        this.body = body;
        this.utenteid = utenteid;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", utenteid=" + utenteid +
                '}';
    }
}
