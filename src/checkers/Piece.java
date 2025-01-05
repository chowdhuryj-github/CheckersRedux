/*
 * Course: SWE2410
 * Fall 2024
 * Lab 2 - Checkers Redux
 * Name: Jawadul Chowdhury
 * Submission Date: 9/16/24
 */
package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Represents a movable piece on the board.
 */
public class Piece {

    private int x;
    private int y;
    private static final float SET_RADIUS_X = 25.0f;
    private static final float SET_RADIUS_Y = 12.0f;
    private static final int PIXEL_DISTANCE = 10;
    private static final int END_OF_BOARD_Y_VALUE = 5;

    /**
     * enum class for color of piece
     */
    public enum Type {
        /**
         * Colors for the Pieces
         */
        RED, BLACK
    }

    private final Type type;
    private PieceBehaviorStrategy pieceBehaviorStrategy;

    /**
     * The position of the piece on the board, where (0,0) is the top left corner
     * position, (BoardController.BOARD_WIDTH-1,0) is the top right corner
     * and (BOARD_WIDTH-1,BOARD_WIDTH-1) is the bottom right corner.
     */
    private final Ellipse ellipse;
    private final Ellipse kingEllipse;

    private boolean isKing;

    /**
     * constructor for Piece
     * @param type type
     * @param x x
     * @param y y
     */
    public Piece(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        if(type.equals(Type.RED)) {
            pieceBehaviorStrategy = new RedPieceBehavior();
        }
        if(type.equals(Type.BLACK)) {
            pieceBehaviorStrategy = new BlackPieceBehavior();
        }
        ellipse = createEllipse();
        kingEllipse = createEllipse();
        kingEllipse.setVisible(false);
        setActive(false);
        reposition();
        BoardController.getSquare(x, y).placePiece(this);
    }

    /**
     * overloaded constructor for piece
     * @param type type
     * @param x x
     * @param y y
     * @param isKing isKing
     */
    public Piece(Type type, int x, int y, boolean isKing) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.isKing = isKing;
        ellipse = createEllipse();
        kingEllipse = createEllipse();
        kingEllipse.setVisible(false);
        reposition();
        BoardController.getSquare(x, y).placePiece(this);
        if(type.equals(Type.RED)) {
            pieceBehaviorStrategy = new RedPieceBehavior();
        }
        if(type.equals(Type.BLACK)) {
            pieceBehaviorStrategy = new BlackPieceBehavior();
        }

    }

    private Ellipse createEllipse() {
        final Ellipse ellipse;
        ellipse = new Ellipse();
        ellipse.setRadiusX(SET_RADIUS_X);
        ellipse.setRadiusY(SET_RADIUS_Y);
        ellipse.setStroke(Color.WHITE);
        if(this.type == Type.RED) {
            ellipse.setFill(Color.RED);
        } else if(this.type == Type.BLACK) {
            ellipse.setFill(Color.BLACK);
        } else {
            throw new IllegalArgumentException("Unknown type:"+type);
        }
        ellipse.setOnMouseClicked(event -> trySetActive());
        BoardController.addChild(ellipse);
        return ellipse;
    }

    /**
     * method to print location of piece
     * @return string
     */
    public String toString() {
        return "Piece at "+x+", "+y;
    }

    public Type getType() {
        return type;
    }

    private void reposition() {
        ellipse.setLayoutX(x* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
        ellipse.setLayoutY(y* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2 +
                PIXEL_DISTANCE);
        kingEllipse.setLayoutX(x* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
        kingEllipse.setLayoutY(y* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
    }

    private void trySetActive() {
        BoardController.trySetActive(this);
    }

    /**
     * method to activate a piece
     * @param isActive isActive
     */
    public void setActive(boolean isActive) {
        if(isActive) {
            ellipse.setStrokeWidth(3);
            kingEllipse.setStrokeWidth(3);
        } else {
            ellipse.setStrokeWidth(1);
            kingEllipse.setStrokeWidth(1);
        }
    }

    /**
     * If possible, perform either an ordinary or capture move to the given square.
     * (Report the problem to the user if not possible)
     * @param square the square to which the move will be made if possible.
     */
    public void tryMove(Square square) {
        if(square.getPiece() != null) {
            BoardController.setMessage("That location is already occupied!\n" +
                    "Please select a different location or piece.");
        } else {
            if (isValidOrdinaryMove(square)) {
                move(square);
            } else if (isValidCapture(square, this.type)) {
                captureMoveTo(square);
            } else {
                BoardController.setMessage("The piece can neither move nor " +
                        "capture to that position" +
                        ".\nPlease try a " +
                        "different " +
                        "square.");
            }
        }
    }

    /**
     * Perform an ordinary move.  Move this piece to the new position and switch turns.
     * Preconditions:
     * The move must be valid -- a valid unoccupied square must be provided.
     *
     * @param square the position to which this piece will be moved.
     */
    private void move(Square square) {
        BoardController.getSquare(x, y).removePiece();
        placeOnSquare(square);
        BoardController.switchTurns();
        setActive(false);

        if(type.equals(Type.BLACK) && y == 0) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Black Piece is now a King");
            pieceBehaviorStrategy = new KingPieceBehavior();
        } else if(type.equals(Type.RED) && y == END_OF_BOARD_Y_VALUE) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Red Piece is now a King");
            pieceBehaviorStrategy = new KingPieceBehavior();
        }
    }

    private void placeOnSquare(Square square) {
        x = square.getX();
        y = square.getY();
        BoardController.getSquare(x, y).placePiece(this);
        reposition();
    }

    /**
     * Perform a capture move.  Identify the piece to be captured
     * and move to that square. Remove the captured piece.
     * Preconditions:
     * The move must be valid -- the place moved to must exist and there must be a piece to capture.
     *
     * @param square A square to which this piece is able to move and capture at the same time.
     * @throws  IllegalArgumentException If no capture is made by moving to the square.
     */
    private void captureMoveTo(Square square) {
        Piece captured = getCapturedPiece(square);
        if(captured == null) {
            throw new IllegalArgumentException("Cannot capture by moving to "+square);
        }
        captured.removeSelf();
        move(square);
    }

    /**
     * Removes this piece from the board.
     */
    public void removeSelf() {
        BoardController.getSquare(x, y).removePiece();
        BoardController.removeChild(ellipse);
        BoardController.removeChild(kingEllipse);
    }

    /**
     * Method for checking if a move is made correctly
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square
     * @throws IllegalStateException IllegalStateException
     */
    public boolean isValidOrdinaryMove(Square square) {
        return pieceBehaviorStrategy.isValidOrdinaryMove(square, this);
    }


    /**
     * Method for checking if the capture is valid or not
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square and capture another
     *    piece at the same time.
     */
    private boolean isValidCapture(Square square, Type capturer) {
        return getCapturedPiece(square) != null && !capturer.equals(getCapturedPiece(square).type);
    }

    /**
     * Find the piece that would be captured by moving this piece to a given square.
     * The piece is not actually captured when calling this method.
     * It is simply identified by calling this method.
     *
     * @param square The square to which a move will be mode
     * @return null if the move cannot be made.
     *      Otherwise, return the piece that would be removed by moving to that square.
     * @throws IllegalStateException IllegalStateException
     */
    public Piece getCapturedPiece(Square square) {
        return pieceBehaviorStrategy.getCapturedPiece(square, this);
    }

    private Piece getMiddlePiece(Square square) {
        int middleX = (square.getX() + x) / 2;
        int middleY = (square.getY() + y) / 2;
        return BoardController.getSquare(middleX, middleY).getPiece();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

