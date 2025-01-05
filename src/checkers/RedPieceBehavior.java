/*
 * Course: SWE2410
 * Fall 2024
 * Lab 2 - Checkers Redux
 * Name: Jawadul Chowdhury
 * Submission Date: 9/16/24
 */
package checkers;

/**
 * class for RedPieceBehavior
 */
public class RedPieceBehavior extends SharedPieceActions {

    /**
     * Find the piece that would be captured by moving this piece to a given square.
     * The piece is not actually captured when calling this method.
     * It is simply identified by calling this method.
     *
     * @param square The square to which a move will be mode
     * @return null if the move cannot be made.
     * Otherwise, return the piece that would be removed by moving to that square.
     * @throws IllegalStateException IllegalStateException
     */
    @Override
    public Piece getCapturedPiece(Square square, Piece piece) {
        if (!((square.getY() == piece.getY() + 2 && Math.abs(square.getX()-piece.getX()) == 2))) {
            return null;
        } else {
            return getMiddlePiece(square, piece);
        }
    }

    /**
     * Method for checking if a move is made correctly
     *
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square
     * @throws IllegalStateException IllegalStateException
     */
    @Override
    public boolean isValidOrdinaryMove(Square square, Piece piece) {
        return square.getY() == piece.getY() + 1 && Math.abs(square.getX()-piece.getX()) == 1;
    }

}
