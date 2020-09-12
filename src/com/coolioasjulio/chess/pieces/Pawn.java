package com.coolioasjulio.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.coolioasjulio.chess.Board;
import com.coolioasjulio.chess.Move;
import com.coolioasjulio.chess.Square;
import com.coolioasjulio.chess.exceptions.InvalidMoveException;

public class Pawn extends Piece {

    public Pawn(Square square, int team, Board board) {
        super(square, team, board);
    }

    public double getRawValue() {
        return Piece.PAWN_VALUE;
    }

    public double getVanillaValue() {
        return Piece.VANILLA_PAWN_VALUE;
    }

    private boolean inRange(int a, int min, int max) {
        return min <= a && a <= max;
    }

    @Override
    public Move[] getMoves() {
        int x = square.getX();
        int y = square.getY();
        List<Move> moves = new ArrayList<>();
        if (inRange(y, 2, 7)) {
            Square inFront = new Square(x, y + team);
            if (board.checkSquare(inFront) == null) {
                if (inRange(inFront.getY(), 2, 7)) {
                    moves.add(new Move(this, inFront));
                    if (!moved && board.checkSquare(new Square(x, y + 2 * team)) == null) {
                        moves.add(new Move(this, new Square(x, y + 2 * team)));
                    }
                } else {
                    // deal with promotion
                    moves.add(new Promotion(this, inFront, "Queen"));
                    moves.add(new Promotion(this, inFront, "Rook"));
                    moves.add(new Promotion(this, inFront, "Bishop"));
                    moves.add(new Promotion(this, inFront, "Knight"));
                }
            }
        }

        if (inRange(x - 1, 0, 7)) {
            Square end = new Square(x - 1, y + team);
            Piece p = board.checkSquare(end);
            if (p != null && p.team != team)
                moves.add(new Move(this, end, true));
        }
        if (inRange(x + 1, 0, 7)) {
            Square end = new Square(x + 1, y + team);
            Piece p = board.checkSquare(end);
            if (p != null && p.team != team)
                moves.add(new Move(this, end, true));
        }
        return moves.toArray(new Move[0]);
    }

    public String toString() {
        return super.getSquare().toString();
    }

    public Pawn copy() {
        Pawn pawn = new Pawn(this.square, this.team, this.board);
        pawn.moved = moved;
        return pawn;
    }

    private static class Promotion extends Move {
        private final String promotion;

        public Promotion(Pawn pawn, Square end, String promotion) {
            super(pawn, end);
            this.promotion = promotion;
        }

        @Override
        public void doMove(Board board) {
            board.removePiece(board.checkSquare(start));
            Piece p;
            switch (promotion) {
                case "Queen":
                    p = new Queen(end, team, board);
                    break;
                case "Rook":
                    p = new Rook(end, team, board);
                    break;
                case "Bishop":
                    p = new Bishop(end, team, board);
                    break;
                case "Knight":
                    p = new Knight(end, team, board);
                    break;
                default:
                    throw new InvalidMoveException("Unrecognized piece!");
            }
            p.moved = true;
            board.addPiece(p);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Promotion && super.equals(o);
        }

        @Override
        public String toString() {
            return super.toString() + "=" + (promotion.equals("Knight") ? "N" : promotion.substring(0, 1));
        }
    }
}