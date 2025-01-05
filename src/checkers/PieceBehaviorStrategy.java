/*
 * Course: SWE2410
 * Fall 2024
 * Lab 2 - Checkers Redux
 * Name: Jawadul Chowdhury
 * Submission Date: 9/16/24
 */
package checkers;

/**
 * interface for behavior of red, black & king pieces
 */
public interface PieceBehaviorStrategy {

    /**
     * Find the piece that would be captured by moving this piece to a given square.
     * The piece is not actually captured when calling this method.
     * It is simply identified by calling this method.
     *
     * @param square The square to which a move will be mode
     * @param piece the piece which will be captured
     * @return null if the move cannot be made.
     *      Otherwise, return the piece that would be removed by moving to that square.
     * @throws IllegalStateException IllegalStateException
     */
    Piece getCapturedPiece(Square square, Piece piece);


    /**
     * Method for checking if a move is made correctly
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square
     * @throws IllegalStateException IllegalStateException
     */
    boolean isValidOrdinaryMove(Square square, Piece piece);


}
