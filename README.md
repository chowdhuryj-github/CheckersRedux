[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/ySCT0iTa)

# Lab 2: Checkers Redux [ Done ]
In this lab, you will refactor your code from Lab 1, so it uses the Strategy Pattern. This will demonstrate how the pattern
can improve cohesion.

## The First Three Steps
- [x] Fix any issues noted by your instructor in the previous lab's submission
- [x] Create an interface or abstract class for ```MoveBehavior```. It must have ```isValidOrdinaryMove``` and ```getCapturedPiece``` methods
  in it
- [x] Create classes derived from ```MoveBehavior```. These will reflect the different ways to move pieces. You will implement the behaviors
  below; write skeleton classes for now

### Solution to First Three Steps
The following solutions are listed below here for the steps that are listed above:

1. Fix any issues noted by your instructor in the previous lab's submission
   The reviews have been made and the changes have been corrected

2. Create an interface or abstract class for ```MoveBehavior```. It must have ```isValidOrdinaryMove``` and ```getCapturedPiece``` methods
   in it
```
public interface PieceBehaviorStrategy {

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
    Piece getCapturedPiece(Square square);

    /**
     * Method for checking if a move is made correctly
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square
     * @throws IllegalStateException IllegalStateException
     */
    boolean isValidOrdinaryMove(Square square);

}

```

3. Create classes derived from ```MoveBehavior```. These will reflect the different ways to move pieces. You will implement the behaviors
   below; write skeleton classes for now
```
BlackPieceBehavior()
RedPieceBehavior()
KingPieceBehavior()
```


## The Last Three Steps
- [x] Introduce a ```MoveBehavior``` member in ```Piece```. This member must be updated each time there is a change in the boolean used to
  capture whether the piece represents a king
- [x] Design & implement the behavior classes you introduced. This includes determining whether to use abstract or interface, determining how they
  will access the piece they control, determining each method's arguments and return values, and deciding if anything needs to be moved or made
  public
- [x] Eliminate control coupling and logic cohesion from the ```isValidOrdinaryMove``` and ```getCapturedPiece``` methods. That is, these methods
  should have no if statements based on whether the piece represents a king. (There will be places in your final solutions where there are
  if statements checking a piece's color; it is only the if statements checking for king pieces in these methods that must be removed)


### Solution to Last Three Steps
1. Introduce a ```MoveBehavior``` member in ```Piece```. This member must be updated each time there is a change in the boolean used to
   capture whether the piece represents a king
```
private PieceBehaviorStrategy pieceBehaviorStrategy;
```

2. Design & implement the behavior classes you introduced. This includes determining whether to use abstract or interface, determining how they
   will access the piece they control, determining each method's arguments and return values, and deciding if anything needs to be moved or made
   public
```
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


```

3. Eliminate control coupling and logic cohesion from the ```isValidOrdinaryMove``` and ```getCapturedPiece``` methods. That is, these methods
   should have no if statements based on whether the piece represents a king. (There will be places in your final solutions where there are
   if statements checking a piece's color; it is only the if statements checking for king pieces in these methods that must be removed)

```
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

```



# Lab One: Checkers Pieces [ Done ]
In this lab, you will explore a maintenance task that does not take advantage of patterns. The goals of this lab include
refreshing skills with Java and JavaFX and to encounter coding challenges that are addressed by introducing design patterns.

In checkers, pieces move diagonally, staying on the same color squares. Each move is to an empty square. When that square
is adjacent, the piece moves just one square. If an adjacent square is occupied by an opponent’s piece and the square past
that (in a straight line) is unoccupied, the piece can capture the opponent’s piece by jumping it. At the start of the game,
all pieces are regular pieces, meaning they can only move towards the opponent’s side.

In this lab you will add support for kings. Once a piece reaches the opponent’s side of the board, the piece is “kinged” by
placing a second checker on it. Kings can then move both forwards and backwards. This is the only difference; they must stay
on the same color squares (moving diagonally) and can only move to an adjacent square or jump one opponent piece.

**Note:** this readme file may not be upto date with new changes made to the GitHub repository. To see new changes, check the
files and lines of code from ``Piece.java`` itself. 

## The First Six Steps
The following steps add king pieces to the game. All changes in this lab should be to the file ```Piece.java```.
- [x] Identify the place in the code where the type of piece is defined.
- [x] Add a boolean at that place to track whether a piece is a “king”.
- [x] Add an optional parameter to the Piece constructor that allows clients to create pieces as kings from the start. By default, pieces are not kings. As you do this, keep the DRY principle in mind!
- [x] Identify the place in the code where it determines that a black piece has reached the last row of the board.
- [x] Modify this code to identify when a red piece has reached the last row as it moves in the opposite direction across the board.
- [x] When a piece reaches one of these positions, update the piece’s state to indicate it now represents a king.

### Solution to First Six Steps
The following solutions are listed below here for the steps that are listed above. These are changes in ```Piece.java```
1. Identify the place in the code where the type of piece is defined:
```
    public enum Type {
        RED, BLACK
    }
    private final Type type;
```
2. Add a boolean at that place to track whether a piece is a “king”.
```
    private boolean isKing;
```
3. Add an optional parameter to the Piece constructor that allows clients to create pieces as kings from the start. By default, pieces are not kings. As you do this, keep the DRY principle in mind!
```
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

    }
```
4. Identify the place in the code where it determines that a black piece has reached the last row of the board.
```
    private void move(Square square) {
        BoardController.getSquare(x, y).removePiece();
        placeOnSquare(square);
        BoardController.switchTurns();
        setActive(false);

        if(type.equals(Type.BLACK) && y == 0) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Black Piece is now a King");
        } else if(type.equals(Type.RED) && y == 5) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Red Piece is now a King");
        }
    }
```

5. Modify this code to identify when a red piece has reached the last row as it moves in the opposite direction across the board.
```
else if(type.equals(Type.RED) && y == 5) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Red Piece is now a King");
       }
```

6. When a piece reaches one of these positions, update the piece’s state to indicate it now represents a king.
```
        if(type.equals(Type.BLACK) && y == 0) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Black Piece is now a King");
        } else if(type.equals(Type.RED) && y == 5) {
            this.isKing = true;
            kingEllipse.setVisible(true);
            BoardController.setMessage("The Red Piece is now a King");
        }
```


## The Second Four Steps
The following steps add king pieces to the game. All changes in this lab should be to the file ```Piece.java```.
- [x] Identify the place in the code which creates the ellipse and places it on the screen. (See also step 9.)
- [x] Modify this code to draw two ellipses instead of one for kings. Place the second ellipse (known as the “crown”) ten pixels above the first ellipse. To implement this, note the createEllipse method can be called twice. Alternatively, you might find it useful to introduce a Pane object to treat the pair of ellipses as a single item. One trick you might find useful is to create graphics objects but set them “invisible” so they do not show when drawn, then setting them “visible” later. No matter how you implement your solution, both ellipses must be selectable and both must be highlighted when selected. Be careful to match details. For example, the lower ellipse must be partially occluded by the upper and there needs to be an appropriate separation between the ellipses.
- [x] Identify the code which places the ellipse at the correct location on the screen.
- [x] Modify the code to place the “crown” piece in the correct place on the screen.

### Solution to Second Four Steps
The following solutions are listed below here for the steps that are listed above. These are changes in ```Piece.java```.
1. Identify the place in the code which creates the ellipse and places it on the screen. (See also step 9.)
```
    private Ellipse createEllipse() {
        final Ellipse ellipse;
        ellipse = new Ellipse();
        ellipse.setRadiusX(25.0f);
        ellipse.setRadiusY(12.0f);
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
```
2. Modify this code to draw two ellipses instead of one for kings. Place the second ellipse (known as the “crown”) ten pixels above the first ellipse. To implement this, note the createEllipse method can be called twice. Alternatively, you might find it useful to introduce a Pane object to treat the pair of ellipses as a single item. One trick you might find useful is to create graphics objects but set them “invisible” so they do not show when drawn, then setting them “visible” later. No matter how you implement your solution, both ellipses must be selectable and both must be highlighted when selected. Be careful to match details. For example, the lower ellipse must be partially occluded by the upper and there needs to be an appropriate separation between the ellipses.
```
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

    }
```

3. Identify the code which places the ellipse at the correct location on the screen.
```
    private void reposition() {
        ellipse.setLayoutX(x* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
        ellipse.setLayoutY(y* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2 + 10);
    }
```

4. Modify the code to place the “crown” piece in the correct place on the screen.
```
    private void reposition() {
        ellipse.setLayoutX(x* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
        ellipse.setLayoutY(y* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2 + 10);
        kingEllipse.setLayoutX(x* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
        kingEllipse.setLayoutY(y* BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2);
    }
```


## The Last Six Steps
The following steps add king pieces to the game. All changes in this lab should be to the file ```Piece.java```.
- [x] Identify the place in the code where it determines if an ordinary move is valid.
- [x] Update this code to work for kings; that is, so that kings can move diagonally towards either side.
- [x] Identify the place in the code where it determines if a capture move is valid.
- [x] Update this code to work for kings. As for regular pieces, a king must jump a piece (on the diagonal) to capture it, but the jump can be towards either side of the board.
- [x] Identify the place in the code where a piece is visually removed from the board.
- [x] Update this code to work for kings; both ellipses need to be removed when a king is captured.

### Solution to Last Six Steps
The following solutions are listed below here for the steps that are listed above. These are changes in ```Piece.java```.
1. Identify the place in the code where it determines if an ordinary move is valid.
```
    public boolean isValidOrdinaryMove(Square square) {
        if(isKing) {
            return Math.abs(square.getY() - y) == 1 &&
                    Math.abs(square.getX() - x) == 1;
        }
        if(type.equals(Type.BLACK)) {
            return (square.getY() == y - 1 &&
                    Math.abs(square.getX()-x) == 1);
        } else if(type.equals(Type.RED)){
            return (square.getY() == y + 1 &&
                    Math.abs(square.getX()-x) == 1);
        } else {
            throw new IllegalStateException("This piece has an unknown type:"+type);
        }
    }
```

2. Update this code to work for kings; that is, so that kings can move diagonally towards either side.
```
        if(isKing) {
            return Math.abs(square.getY() - y) == 1 &&
                    Math.abs(square.getX() - x) == 1;
        
```

3. Identify the place in the code where it determines if a capture move is valid.
```
    private boolean isValidCapture(Square square) {
        return getCapturedPiece(square) != null;
    }
```

4. Update this code to work for kings. As for regular pieces, a king must jump a piece (on the diagonal) to capture it, but the jump can be towards either side of the board.
```
if(isKing) {
            if(square.getY() == y - 2 && Math.abs(square.getX() - x) == 2 || square.getY() == y + 2 &&
                    Math.abs(square.getX() - x) == 2) {
                return getMiddlePiece(square);
            }
```

5. Identify the place in the code where a piece is visually removed from the board.
```
    public void removeSelf() {
        BoardController.getSquare(x,y).removePiece();
        BoardController.removeChild(ellipse);
        BoardController.removeChild(kingEllipse);
    }
```

6.  Update this code to work for kings; both ellipses need to be removed when a king is captured.
```
BoardController.removeChild(kingEllipse);
```