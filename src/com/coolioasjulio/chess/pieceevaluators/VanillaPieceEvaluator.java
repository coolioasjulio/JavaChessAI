package com.coolioasjulio.chess.pieceevaluators;

import com.coolioasjulio.chess.pieces.Piece;

public class VanillaPieceEvaluator implements PieceEvaluator {

    @Override
    public double getValue(Piece piece) {
        return piece.getVanillaValue();
    }
}
