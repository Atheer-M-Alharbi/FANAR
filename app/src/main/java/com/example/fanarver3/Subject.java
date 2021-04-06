package com.example.fanarver3;

public class Subject {
    private String subjectTitle;
    private String subject;
    private String authorName;

    public Subject(String subjectTitle, String subject, String authorName) {
        this.subjectTitle = subjectTitle;
        this.subject = subject;
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubjectDescription(){
        String [] d=subject.split(" ");
        String description="";
        for (int i=0;i<10&&i<d.length;i++){
            description=description+d[i];
        }
        description=description+"....read more";
        return description;
    }
}
