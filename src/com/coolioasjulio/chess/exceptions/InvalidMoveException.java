package com.coolioasjulio.chess.exceptions;

public class InvalidMoveException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String s) {
        super(s);
    }
}
