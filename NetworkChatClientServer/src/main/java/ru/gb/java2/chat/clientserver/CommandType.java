package ru.gb.java2.chat.clientserver;

public enum CommandType {
    AUTH,
    AUTH_OK,
    AUTH_TIME_OUT,
    ERROR,
    PUBLIC_MESSAGE,
    PRIVATE_MESSAGE,
    CLIENT_MESSAGE,
    END,
    UPDATE_USERS_LIST,
    CHANGE_NICK,
    CHANGE_NICK_OK
}
