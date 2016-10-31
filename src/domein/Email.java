/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Remko
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Email.geefEmailMetStatus",query = "select e from Email e where e.status = :status"),
    @NamedQuery(name = "Email.geefAlleEmails",query = "select e from Email e")
})
public class Email implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emailId;
    private int status;
    private String header;
    private String body;
    private String footer;
    private String subject;

    protected Email() {
    }

    public Email(int status, String header, String body, String footer, String subject) {
        this.status = status;
        this.header = header;
        this.body = body;
        this.footer = footer;
        this.subject = subject;
    }

    public int getStatus() {
        return status;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getFooter() {
        return footer;
    }

    public String getSubject() {
        return subject;
    }
    
    
    
}
