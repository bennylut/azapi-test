/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.lab;

import bgu.dcr.az.lab.data.Users;
import bgu.dcr.az.lab.db.DBManager;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Inna
 */
@ManagedBean(eager = true)
@SessionScoped
public class SimpleLogin {

    String email = "";
    String password = "";
    SimpleLogin user;

    public SimpleLogin() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String checkValidUser() {
        Users u = DBManager.UNIT.isVerifiedUserCredentials(this.email, this.password);
        if (u !=null) {
            SessionManager.putInSessionMap(SessionManager.CURRENT_USER, u);
            return "Welcome";
        }
        return "Laboratory";
    }
}