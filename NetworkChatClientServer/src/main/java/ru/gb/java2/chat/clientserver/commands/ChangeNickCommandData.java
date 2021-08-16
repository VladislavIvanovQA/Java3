package ru.gb.java2.chat.clientserver.commands;

import java.io.Serializable;

public class ChangeNickCommandData implements Serializable {
    private final String nick;

    public ChangeNickCommandData(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return nick;
    }

    @Override
    public String toString() {
        return "ChangeNickCommand{" +
                "nick='" + nick + '\'' +
                '}';
    }
}
