package com.noiunina.presenter;

import com.noiunina.model.GestoreRichieste;
import com.noiunina.view.IRegisterView;


public class RegisterPresenter implements IRegisterPresenter{

    public static IRegisterView iRegisterView;

    public RegisterPresenter(IRegisterView view){

        iRegisterView = view;

    }

    public RegisterPresenter(){

    }


    public void effettuaRegistrazione(String nome, String cognome, String corso, String email, String pwd, String pwd2){

        if(nome.isEmpty()){
            iRegisterView.showNameError();
        }
        else if(cognome.isEmpty()){
            iRegisterView.showSurnameError();
        }
        else if(email.isEmpty() | !isEmailValid(email)){
            iRegisterView.showEmailError();
        }
        else if(pwd.isEmpty()){
            iRegisterView.showPwdError();
        }
        else if(!pwd.equals(pwd2)){
            iRegisterView.showPwdMismatchError();
        }
        else if(pwd.length()<6){
            iRegisterView.showPwdErrorNumberChar();
        }
        else{
            GestoreRichieste sys = GestoreRichieste.getInstance();
            sys.richiestaRegistrazione(nome, cognome, corso, email, pwd);

        }
    }


    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void registrazioneEseguitaConSuccesso() {
        iRegisterView.getLoginActivity();
    }

    @Override
    public void registrazioneFallita() {

        iRegisterView.showRegistrationError();

    }



}
