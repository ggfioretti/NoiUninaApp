package com.noiunina.view;

public interface IRegisterView {
    void showRegistrationError();
    void getLoginActivity();
    void showNameError();
    void showSurnameError();
    void showEmailError();
    void showPwdError();
    void showPwdMismatchError();
    void showPwdErrorNumberChar();
}
