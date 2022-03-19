package de.wohlers.strom.administration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailAccount {

    private int                  id;
    private String               senderName;
    private String               login;
    private String               secret;
    private String               host;
    private int                  port;
    private AuthenticationMethod method;
    private ConnectionSecurity   security;

    public enum AuthenticationMethod {
        NONE, PASSWORD_NORMAL, PASSWORD_ENCRYPTED, KERBEROS_GSSAPI, NTLM, O_AUTH
    }

    public enum ConnectionSecurity {
        NONE, STARTTLS, SSL_TLS
    }
}
