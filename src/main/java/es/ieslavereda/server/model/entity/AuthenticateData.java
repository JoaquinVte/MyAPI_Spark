package es.ieslavereda.server.model.entity;

public class AuthenticateData {
    private String email;
    private String paswd;

    public AuthenticateData(String email, String paswd) {
        this.email = email;
        this.paswd = paswd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaswd() {
        return paswd;
    }

    public void setPaswd(String paswd) {
        this.paswd = paswd;
    }
}
