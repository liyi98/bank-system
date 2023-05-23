package com.bank.app.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bank.app.domain.BankUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankUserLoginDTO implements Serializable {

    private String login;

    private String password;

    private boolean isRememberMe;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return isRememberMe;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankUserLoginDTO{" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
