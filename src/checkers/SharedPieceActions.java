/*
 * Course: SWE2410
 * Fall 2024
 * Lab 2 - Checkers Redux
 * Name: Jawadul Chowdhury
 * Submission Date: 9/16/24
 */
package checkers;

/**
 * abstract class for SharedPieceActions
 */
public abstract class SharedPieceActions implements PieceBehaviorStrategy {

    protected Piece getMiddlePiece(Square square, Piece piece) {
        int middleX = (square.getX() + piece.getX()) / 2;
        int middleY = (square.getY() + piece.getY()) / 2;
        return BoardController.getSquare(middleX, middleY).getPiece();
    }


    /**
     * method for getCapturedPiece
     * @param square square
     * @return piece
     */
    public abstract Piece getCapturedPiece(Square square, Piece piece);
}

