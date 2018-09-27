package com.coolioasjulio.chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HumanGUIPlayer implements Player, MouseListener {
    
    private int team;
    private Chess chess;
    private Board board;
    private Square fromSquare, toSquare;
    private Object lock = new Object();
    public HumanGUIPlayer(int team, Board board, Chess chess) {
        this.team = team;
        this.board = board;
        this.chess = chess;
        chess.addMouseListener(this);
    }

    @Override
    public Move getMove() {
        // Wait for the mouse click
        synchronized(lock) {
            try {
                fromSquare = null;
                toSquare = null;
                System.out.println("It is " + ((team == Piece.WHITE) ? "white" : "black")
                        + "'s turn!");
                System.out.println("Click the piece you want to move!");
                lock.wait();
                System.out.println("Click the square you want to move to!");
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        Piece piece = board.checkSquare(fromSquare);
        if(piece.team != team) {
            throw new InvalidMoveException("Choose your own piece!");
        }
        
        Move move = new Move(piece, fromSquare, toSquare, board.checkSquare(toSquare) != null);
        
        return move;
    }

    @Override
    public int getTeam() {
        return team;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int squareX = e.getX() / chess.getTileSize();
        int squareY = (chess.getWidth() - e.getY()) / chess.getTileSize() + 1;
        Square square = new Square(squareX, squareY);
        synchronized(lock) {            
            if(fromSquare == null) {
                Piece p = board.checkSquare(square);
                if(p != null && p.getTeam() == team) {    
                    fromSquare = square;
                    lock.notifyAll();
                }
            } else if(toSquare == null){
                toSquare = square;
                lock.notifyAll();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
