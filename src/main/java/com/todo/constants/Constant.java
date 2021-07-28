package com.todo.constants;

public class Constant {

    private Constant() {
    }

    public enum Status {
        SUCCESS, ERROR;
    }

    public enum TodoAction {
        CREATE_TODO, READ_TODO, UPDATE_TODO, DELETE_TODO;
    }

    public static final String INTERNAL_SERVER_ERROR = "Internal server error. Please contact admin";
}
