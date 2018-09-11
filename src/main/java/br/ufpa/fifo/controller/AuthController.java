package br.ufpa.fifo.controller;

import br.ufpa.fifo.controller.util.JsfUtil;

/**
 *
 * @author thiago
 */
@javax.faces.bean.ManagedBean
@javax.faces.bean.SessionScoped
public class AuthController implements java.io.Serializable {

    private boolean authorized;
    private String passwd;
    private int attempts;

    public AuthController() {
        authorized = false;
        passwd = null;
    }

    public String auth() {
        if (passwd == null || passwd.equals("")) {
            JsfUtil.addErrorMessage("É necessário informar a senha.");
        } else if (passwd.equals("lacs@ufpa")) {
            passwd = null;
            attempts = 0;
            authorized = true;
        } else {
            attempts++;
            JsfUtil.addErrorMessage("Senha incorreta.");
        }
        return "/admin?faces-redirect=true";
    }

    public String unAuth() {
        authorized = false;
        return "/auth?faces-redirect=true";
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getAttempts() {
        return attempts;
    }

}
