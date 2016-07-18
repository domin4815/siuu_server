package pl.edu.agh.siuu.meetings.users.model;

public class TokenString {
    private String string;

    public TokenString(String string) {
        this.string = string;
    }

    public TokenString() {

    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}