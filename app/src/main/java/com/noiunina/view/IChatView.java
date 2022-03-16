package com.noiunina.view;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IChatView {

    void getCurrentChatTitle(String chatTitle);
    void refreshRecyclerView(ArrayList<String> listaTestoMessaggi, ArrayList<String> listaMittenti, ArrayList<String> listaUid);
    void clearEditText();
    void errorInvioMessaggio();
    void erroreAggiornamentoMessaggio();
}
