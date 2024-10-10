package universitysystem.interfaces;

import java.io.IOException;

public interface CanLogin extends Runnable {
    String getLogin();
    String getPassword();
    void login();
    void logout();
    void save() throws IOException;
}
