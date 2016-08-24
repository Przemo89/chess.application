package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;

/**
 * Class for managing of basic operations on the Chess Board.
 *
 * @author Michal Bejm
 *
 */
public class BoardManager {

	private Board board = new Board();
	private ValidateMoveInnerClass moveValidator =  new ValidateMoveInnerClass();
	
	/**Those coordinates will be setup everytime when new board is constructed (in the end every constructor)
	 * and after VALIDATED move of king
	 */
	private Coordinate whiteKingCoordinate;
	private Coordinate blackKingCoordinate;

	public BoardManager() {
		initBoard();
		setKingsInitialCoordinates();
	}

	public BoardManager(List<Move> moves) {
		initBoard();
		for (Move move : moves) {
			addMove(move);
		}
		setKingsInitialCoordinates();
	}

	public BoardManager(Board board) {
		this.board = board;
		setKingsInitialCoordinates();
	}

	/**
	 * Getter for generated board
	 *
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Performs move of the chess piece on the chess board from one field to
	 * another.
	 *
	 * @param from
	 *            coordinates of 'from' field
	 * @param to
	 *            coordinates of 'to' field
	 * @return move object which includes moved piece and move type
	 * @throws InvalidMoveException
	 *             in case move is not valid
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException {

		Move move = validateMove(from, to);

		addMove(move);

		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 */
	public BoardState updateBoardState() {

		Color currentPlayerColor = getCurrentPlayerColor();

		boolean isKingInCheck = isKingInCheck(currentPlayerColor);
		boolean isAnyMoveValid = isAnyMoveValid(currentPlayerColor);

		BoardState boardState;
		if (isKingInCheck) {
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
		} else {
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		this.board.setState(boardState);
		return boardState;
	}

	/**
	 * Checks threefold repetition rule (one of the conditions to end the chess
	 * game with a draw).
	 *
	 * @return true if current state repeated at list two times, false otherwise
	 */
	public boolean checkThreefoldRepetitionRule() {

		// there is no need to check moves that where before last capture/en
		// passant/castling
		int lastNonAttackMoveIndex = findLastNonAttackMoveIndex();
		List<Move> omittedMoves = this.board.getMoveHistory().subList(0, lastNonAttackMoveIndex);
		BoardManager simulatedBoardManager = new BoardManager(omittedMoves);

		int counter = 0;
		for (int i = lastNonAttackMoveIndex; i < this.board.getMoveHistory().size(); i++) {
			Move moveToAdd = this.board.getMoveHistory().get(i);
			simulatedBoardManager.addMove(moveToAdd);
			boolean areBoardsEqual = Arrays.deepEquals(this.board.getPieces(),
					simulatedBoardManager.getBoard().getPieces());
			if (areBoardsEqual) {
				counter++;
			}
		}

		return counter >= 2;
	}

	/**
	 * Checks 50-move rule (one of the conditions to end the chess game with a
	 * draw).
	 *
	 * @return true if no pawn was moved or not capture was performed during
	 *         last 50 moves, false otherwise
	 */
	public boolean checkFiftyMoveRule() {

		// for this purpose a "move" consists of a player completing his turn
		// followed by his opponent completing his turn
		if (this.board.getMoveHistory().size() < 100) {
			return false;
		}

		for (int i = this.board.getMoveHistory().size() - 1; i >= this.board.getMoveHistory().size() - 100; i--) {
			Move currentMove = this.board.getMoveHistory().get(i);
			PieceType currentPieceType = currentMove.getMovedPiece().getType();
			if (currentMove.getType() != MoveType.ATTACK || currentPieceType == PieceType.PAWN) {
				return false;
			}
		}

		return true;
	}

	private void initBoard() {

		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(1, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(2, 7));
		this.board.setPieceAt(Piece.BLACK_QUEEN, new Coordinate(3, 7));
		this.board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(5, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(6, 7));
		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(7, 7));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(x, 6));
		}

		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(2, 0));
		this.board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(3, 0));
		this.board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(5, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(6, 0));
		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7, 0));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(x, 1));
		}
	}
	
	/**Sets coordinates for both kings in the end of every constructor.
	 */
	private void setKingsInitialCoordinates(){
		for(int i = 0; i < Board.SIZE; i++){
			for(int j = 0; j < Board.SIZE; j++){
				if (this.board.getPieceAt(new Coordinate(i, j)) != null && this.board.getPieceAt(new Coordinate(i, j)) == Piece.WHITE_KING){
					whiteKingCoordinate = new Coordinate(i, j);
				}
				if (this.board.getPieceAt(new Coordinate(i, j)) != null && this.board.getPieceAt(new Coordinate(i, j)) == Piece.BLACK_KING){
					blackKingCoordinate = new Coordinate(i, j);
				}
			}
		}
	}

	private void addMove(Move move) {

		addRegularMove(move);

		if (move.getType() == MoveType.CASTLING) {
			addCastling(move);
		} else if (move.getType() == MoveType.EN_PASSANT) {
			addEnPassant(move);
		}

		this.board.getMoveHistory().add(move);
	}

	private void addRegularMove(Move move) {
		Piece movedPiece = this.board.getPieceAt(move.getFrom());
		this.board.setPieceAt(null, move.getFrom());
		this.board.setPieceAt(movedPiece, move.getTo());

		performPromotion(move, movedPiece);
	}

	private void performPromotion(Move move, Piece movedPiece) {
		if (movedPiece == Piece.WHITE_PAWN && move.getTo().getY() == (Board.SIZE - 1)) {
			this.board.setPieceAt(Piece.WHITE_QUEEN, move.getTo());
		}
		if (movedPiece == Piece.BLACK_PAWN && move.getTo().getY() == 0) {
			this.board.setPieceAt(Piece.BLACK_QUEEN, move.getTo());
		}
	}

	/**
	 * @param move
	 */
	private void addCastling(Move move) {
		if (move.getFrom().getX() > move.getTo().getX()) {
			Piece rook = this.board.getPieceAt(new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() + 1, move.getTo().getY()));
		} else {
			Piece rook = this.board.getPieceAt(new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() - 1, move.getTo().getY()));
		}
	}

	private void addEnPassant(Move move) {
		Move lastMove = this.board.getMoveHistory()
				.get(this.board.getMoveHistory().size() - 1);
		this.board.setPieceAt(null, lastMove.getTo());
	}

	private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {
		return this.moveValidator.validateMove(from, to);
	}
	

	/** This inner class validates moves.
	 * @author PLENIK
	 */
	private class ValidateMoveInnerClass {
		
		private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {
			if (isMoveWithinBoardBounds(from, to) == false) {
				throw new InvalidMoveException("Provided move is out of chessboard.");
			}
			boolean isOnSourceFieldCurrentPlayersPiece = isOnSourceFieldCurrentPlayersPiece(from);
			boolean isOnTargetFieldCurrentPlayersPiece = isOnTargetFieldCurrentPlayersPiece(to);
			checkPreliminaryIfInvalidMoveExceptionOccured(isOnSourceFieldCurrentPlayersPiece, 
					isOnTargetFieldCurrentPlayersPiece);
			
			Move moveToValidate = createMoveToValidateBasingOnCoordinatesFromAndTo(from, to);
			
			boolean canPiecePerformSuchMove = moveToValidate.getMovedPiece().getType().isMovePossible(moveToValidate);
			boolean isCollisionInPathBetweenFromAndToo = checkForCollision(moveToValidate);
			isPieceCanPerformMoveAndIsCollisionDetected(canPiecePerformSuchMove, isCollisionInPathBetweenFromAndToo);
			isKingInCheckExceptionToBeThrown(moveToValidate);
			updateKingsCoordinates(moveToValidate);
			
			return moveToValidate;
		}

		private void isKingInCheckExceptionToBeThrown(Move moveToCalidate) throws KingInCheckException {
			boolean isItLegalMove = simulateAndCheckPotentiallyLegalMoveFinal(moveToCalidate);
			if (isItLegalMove == false) {
					throw new KingInCheckException();
			} 
		}
		
		private void isPieceCanPerformMoveAndIsCollisionDetected(boolean canPiecePerformSuchMove, 
				boolean isCollisionInPathBetweenFromAndToo) throws InvalidMoveException {
			StringBuilder errorMessage = new StringBuilder();
			if (canPiecePerformSuchMove == false) {
				errorMessage.append("This Piece cannot perform such move. ");
			}
			if (isCollisionInPathBetweenFromAndToo == true) {
				errorMessage.append("This Piece can't leap over other Pieces.");
			}
			if (errorMessage.length() != 0) {
				throw new InvalidMoveException(errorMessage.toString());
			}
		}

		/**This method create @move (basing on Coordinates @from and @to, 
		 * after first preliminary validations 
		 * (please see @checkPreliminaryIfInvalidMoveExceptionOccured method))
		 * @param from 
		 * @param to
		 * @return moveToValidate (which will be validated further in @validateMove method)
		 */
		private Move createMoveToValidateBasingOnCoordinatesFromAndTo(Coordinate from, Coordinate to) {
			Move moveToValidate = new Move();
			moveToValidate.setFrom(from);
			moveToValidate.setTo(to);
			setMoveType(moveToValidate);
			setPieceInMoveToVAlidate(moveToValidate);
			return moveToValidate;
		}
		
		/**Thanks to this method, it's possible to avoid:  
		 * @param isMoveWithinBoardBounds - IndexOutOfBoundsException
		 * @param isOnSourceFieldCurrentPlayersPiece - ensure that Player picked his Piece, not his Opponent's
		 * @param isTargetFieldOccupiedByFigureOfTheSameColor - ensure that the Player doesn't want to 
		 * perform Capture move on his Piece
		 * @throws InvalidMoveException - if any above is true
		 */
		private void checkPreliminaryIfInvalidMoveExceptionOccured(boolean isOnSourceFieldCurrentPlayersPiece, 
				boolean isTargetFieldOccupiedByFigureOfTheSameColor) throws InvalidMoveException {
			StringBuilder errorMessage = new StringBuilder();
			if (isOnSourceFieldCurrentPlayersPiece == false) {
				errorMessage.append("Can't perform such move. ");
			}
			if (isTargetFieldOccupiedByFigureOfTheSameColor == true) {
				errorMessage.append("Can't move figure to the field with figure of the same color.");
			}
			if (errorMessage.length() != 0) {
				throw new InvalidMoveException(errorMessage.toString());
			}
			
		}

		/**This method checks for collision in the line between source field to target field.
		 * If source and target field are neighbours or move is for Piece like KNIGHT or PAWN tests 
		 * are being skipped ( @return false)
		 * @param moveToValidate - move which is during validation
		 * @return true if collision has been detected
		 */
		private boolean checkForCollision(Move moveToValidate) {
			if (moveToValidate.getMovedPiece().getType() == PieceType.KNIGHT || moveToValidate.getMovedPiece().getType() == PieceType.PAWN) {
				return false;
			}
			if (Math.abs(moveToValidate.getTo().getY() - moveToValidate.getFrom().getY()) <= 1 
					&& Math.abs(moveToValidate.getTo().getX() - moveToValidate.getFrom().getX()) <= 1) {
				return false;
			}
			int verticalYIterator = verticalYIterator(moveToValidate);
			int horizontalXIterator = horizontalXIterator(moveToValidate);
			for (int x = moveToValidate.getTo().getX() + horizontalXIterator, 
					y = moveToValidate.getTo().getY() + verticalYIterator; 
					x != moveToValidate.getFrom().getX() || y != moveToValidate.getFrom().getY();
					x = x + horizontalXIterator, y = y + verticalYIterator) {
				if (board.getPieceAt(new Coordinate(x, y)) != null) {
					return true;
				}
			}
			return false;
		}
		
		/**This method is used during detecting potential collision and 
		 * assigns direction in vertical line between destination and source fields.
		 * @param moveToValidate
		 * @return
		 */
		private int verticalYIterator(Move moveToValidate){
			if (moveToValidate.getTo().getY() - moveToValidate.getFrom().getY() > 0) {
				return -1;
			}
			if (moveToValidate.getTo().getY() - moveToValidate.getFrom().getY() < 0) {
				return 1;
			}
			return 0;
		}
		
		/**This method is used during detecting potential collision and 
		 * assigns direction in horizontal line between destination and source fields.
		 * @param moveToValidate
		 * @return
		 */
		private int horizontalXIterator(Move moveToValidate){
			if (moveToValidate.getTo().getX() - moveToValidate.getFrom().getX() > 0) {
				return -1;
			}
			if (moveToValidate.getTo().getX() - moveToValidate.getFrom().getX() < 0) {
				return 1;
			}
			return 0;
		}
		
		/**This method updates coordinates @blackKingCoordinate and @whiteKingCoordinate 
		 * if any King moved.
		 * @param validatedMove - It's crucial to pass only VALIDATED MOVE
		 */
		private void updateKingsCoordinates(Move validatedMove) {
			if (board.getPieceAt(validatedMove.getFrom()) == Piece.BLACK_KING) {
				blackKingCoordinate = validatedMove.getTo();
			}
			if (board.getPieceAt(validatedMove.getFrom()) == Piece.WHITE_KING) {
				whiteKingCoordinate = validatedMove.getTo();
			}
		}
		
		/**Checks, whether it's correct move order (white Pieces always should start).
		 * Also checks, if player wanted to "move" from empty field of the chessboard.
		 * @calculateNextMoveColor is used to check which Color plays CURRENTLY.
		 * @param from - source coordination
		 * @throws InvalidMoveException
		 */
		private boolean isOnSourceFieldCurrentPlayersPiece(Coordinate from) throws InvalidMoveException {
			if (board.getPieceAt(from) != null) {
				if (board.getPieceAt(from).getColor() != getCurrentPlayerColor()) {
					return false;
				}
			}
			if (board.getPieceAt(from) == null) {
				return false;
			}
			return true;
		}
		
		/**Checks, if the player wants to capture his own Piece.
		 * @calculateNextMoveColor is used to check which Color plays CURRENTLY
		 * @param to - destination coordination
		 * @throws InvalidMoveException
		 */
		private boolean isOnTargetFieldCurrentPlayersPiece(Coordinate to) {
			if (board.getPieceAt(to) != null) {
				if (board.getPieceAt(to).getColor() == getCurrentPlayerColor()) {
					return true;
				}
			}
			return false;
		}
		
		/**Determine, if move is ATTACK or CAPTURE type.
		 * @param moveToValidate
		 */
		private void setMoveType(Move moveToValidate){
			if (board.getPieceAt(moveToValidate.getTo()) != null){
				moveToValidate.setType(MoveType.CAPTURE);
				return;
			}
			moveToValidate.setType(MoveType.ATTACK);
		}
		
		private void setPieceInMoveToVAlidate(Move moveToValidate){
			moveToValidate.setMovedPiece(board.getPieceAt(moveToValidate.getFrom()));
		}
		
		private boolean isMoveWithinBoardBounds(Coordinate from, Coordinate to) {
			if (to.getX() < 0 || to.getY() < 0 || from.getY() < 0 || from.getX() < 0) {
				return false;
			}
			if (to.getX() > 7 || to.getY() > 7 || from.getY() > 7 || from.getX() > 7) {
				return false;
			}
			return true;
		}

		/**Determines, if some specific field (like the one containing king of some player)
		 * might be attacked by any Opponent's Piece. 
		 * This method is invoked only after fully validated move. There are 2 scenarios, in which this method is crucial:
		 * 1. For example, White player made a  valid move. After that, it is required to see, 
		 * if check/checkmate/stalemate occured for the next player - Black. To do so, it is necessary to see,
		 * if the black king might be attacked by White Pieces:
		 * CASE 1. Black king isn't attacked by White Pieces (isn't in check). 
		 * Now it's required to determine if Black Pieces have 
		 * at least ONE legal move(a move, after which Black king is not in check): 
		 * ->if there is none legal move - stalemate occurs
		 * ->if there is at least ONE legal move - game is continued
		 * CASE 2. Black King is attacked by White Pieces (check). 
		 * The most important to determine if Black player have any legal move of
		 * at least one Black Piece, after which Black King is not in check.
		 * If such move doesn't exist, the game ends ( @CheckMate). 
		 * This method is crucial for checking: @Check, @Checkmate, @Stalemate,
		 * @param examinatedField - field which will be tested
		 * @param byWhichPlayerColorIsAttacked - always should be the opposite color than Piece on checked field
		 * @return true, if field might be attacked by opponent in his next move
		 */
		private boolean isSpecificFieldUnderAttack(Coordinate examinatedField, Color byWhichPlayerColorIsAttacked) {
			for(int x = 0; x < Board.SIZE; x++){
				for(int y = 0; y < Board.SIZE; y++){
					Coordinate currentCoordinate = new Coordinate(x, y);
					if (board.getPieceAt(currentCoordinate) != null 
							&& board.getPieceAt(currentCoordinate).getColor() == byWhichPlayerColorIsAttacked) {
						Move possibleAttackingMove = 
								createMoveToValidateBasingOnCoordinatesFromAndTo(currentCoordinate, examinatedField);
						if (possibleAttackingMove.getMovedPiece().getType().isMovePossible(possibleAttackingMove) == true 
								&& checkForCollision(possibleAttackingMove) == false) {
							return true;
						}
					}
				}
			}
			return false;
		}
		
		/**This method in invoked always after perfoming VALIDATED move, to check if Checkmate/Stalemate occured.
		 * It uses @getCurrentPlayerColor to determine, for which player should be made check for any legal move 
		 * like for example if @WHITE player made a valid move, this method is invoked for @BLACK player
		 * Scans whole chessboard, looking for current's player Pieces, then retrieving generated list 
		 * of potential moves of this piece (list is generated by method @possibleMovesGenerator defined in enum @PieceType).
		 * After retrieving the list, method streams through list, looking for ANY legal move. If there is one,
		 * it stops. If not - keeps looking on potential moves of other current player's pieces. 
		 * If none is found, it's a sign that checkmate or stalemate occured  
		 * @return true, if there is some legal move to be made
		 */
		private boolean isAnyMoveValid() {
			for(int x = 0; x < Board.SIZE; x++){
				for(int y = 0; y < Board.SIZE; y++){
					Coordinate currentCoordinate = new Coordinate(x, y);
					if (board.getPieceAt(currentCoordinate) != null && board.getPieceAt(currentCoordinate).getColor() == getCurrentPlayerColor()) {
						List<Move> generatedMoves = board.getPieceAt(currentCoordinate).getType()
								.possibleMoveGenerator(currentCoordinate, board.getPieceAt(currentCoordinate));
						boolean result = validatePotentialLegalMoves(generatedMoves);
						if (result == true) {
							return true;
						}
					}
				}
			} 
			return false;
		}
		
		/**Validates only potential moves, to check, if game can be continued or Checkmate/Stalemate occured.
		 * @return true if move is legal, false otherwise.
		 */
		private boolean validatePotentialLegalMoves(List<Move> potentialPieceMoves) {
			boolean isMoveLegal = false;
			boolean isMoveLegalInFirstPhase = false;
			for (int i = 0; i < potentialPieceMoves.size(); i++) {
				Move potentiallyLegalMove = potentialPieceMoves.get(i);
				isMoveLegalInFirstPhase = validateSinglePotentialLegalMoveFirstPhase(potentiallyLegalMove);
				if (isMoveLegalInFirstPhase == true) {
					isMoveLegal = simulateAndCheckPotentiallyLegalMoveFinal(potentiallyLegalMove);
					if (isMoveLegal) {
						return true;
					}
				}
			}
			return isMoveLegal;
		}
		
		/**During this method, potentially legal move is simulated and finally evaluated.
		 * @param potentiallyLegalMove
		 * @return true, if move is legal
		 */
		private boolean simulateAndCheckPotentiallyLegalMoveFinal(Move potentiallyLegalMove) {
			Move reversalMove = createReversalMove(potentiallyLegalMove);
			board.setPieceAt(board.getPieceAt(potentiallyLegalMove.getFrom()), potentiallyLegalMove.getTo());
			board.setPieceAt(null, potentiallyLegalMove.getFrom());
			
			boolean isMoveLegal = validatePotentialLegalMoveLastPhase(potentiallyLegalMove);
			
			undoMove(reversalMove);
			return isMoveLegal;
		}
		

		
		/**Creates opposite move to potential move.
		 * @param potentiallyLegalMove
		 * @return
		 */
		private Move createReversalMove(Move potentiallyLegalMove){
			Move reversalMove = new Move().setNewCoordinateFrom(potentiallyLegalMove.getTo())
					.setNewCoordinateTo(potentiallyLegalMove.getFrom());
			if (board.getPieceAt(potentiallyLegalMove.getTo()) != null) {
				reversalMove.setNewMovedPiece(board.getPieceAt(potentiallyLegalMove.getTo()));
			}
			if (board.getPieceAt(potentiallyLegalMove.getFrom()).getType() == PieceType.KING) {
				
			}
			return reversalMove;
		}
		
		/**Reverts potential move, basing on the reversalMove.
		 * @param reversalMove
		 */
		private void undoMove(Move reversalMove){
			board.setPieceAt(board.getPieceAt(reversalMove.getFrom()), reversalMove.getTo());
			if (reversalMove.getMovedPiece() != null) {
				board.setPieceAt(reversalMove.getMovedPiece(), reversalMove.getFrom());
			} else {
				board.setPieceAt(null, reversalMove.getFrom());
			}
		}
		
		/**This method checks if potential legal move can be made in first place.
		 * If yes there is need to check, if the current player's king is suddenly in check after this theoretical move.
		 * If not
		 * @param moveToBeValidated
		 * @return
		 */
		private boolean validateSinglePotentialLegalMoveFirstPhase(Move moveToBeValidated) {
			if (isMoveWithinBoardBounds(moveToBeValidated.getFrom(), moveToBeValidated.getTo()) == false) {
				return false;
			}
			if (validatePawnCapturePotentiallyLegalMove(moveToBeValidated) == true) {
				return true;
			}
			if (isOnTargetFieldCurrentPlayersPiece(moveToBeValidated.getTo()) == true 
					|| checkForCollision(moveToBeValidated) == true) {
				return false;
			}
			return true;
		}

		private boolean validatePawnCapturePotentiallyLegalMove(Move moveToBeValidated) {
			if (moveToBeValidated.getMovedPiece().getType() == PieceType.PAWN && moveToBeValidated.getType() == MoveType.CAPTURE) {
				if (board.getPieceAt(moveToBeValidated.getTo()) != null 
						&& board.getPieceAt(moveToBeValidated.getTo()).getColor() != getCurrentPlayerColor()) {
					return true;
				}
				return false;
			}
			return true;
		}
		
		private boolean validatePotentialLegalMoveLastPhase(Move potentiallyLegalMove) {
			if (board.getPieceAt(potentiallyLegalMove.getTo()) == Piece.BLACK_KING) {
				return !isSpecificFieldUnderAttack(potentiallyLegalMove.getTo(), Color.WHITE);
			}
			if (board.getPieceAt(potentiallyLegalMove.getTo()) == Piece.WHITE_KING) {
				return !isSpecificFieldUnderAttack(potentiallyLegalMove.getTo(), Color.BLACK);
			}
			if (getCurrentPlayerColor() == Color.WHITE) {
				return !isSpecificFieldUnderAttack(whiteKingCoordinate, Color.BLACK);
			} 
			if (getCurrentPlayerColor() == Color.BLACK) {
				return !isSpecificFieldUnderAttack(blackKingCoordinate, Color.WHITE);
			}
			return true;
		}
		
		private Coordinate getKingsCoordinates(Color currentPlayerColor){
			for(int x = 0; x < Board.SIZE; x++){
				for(int y = 0; y < Board.SIZE; y++){
					Coordinate currentExaminatedCoordinate = new Coordinate(x, y);
					if (board.getPieceAt(new Coordinate(x, y)) != null) {
						if (board.getPieceAt(new Coordinate(x, y)).getType() == PieceType.KING 
								&& board.getPieceAt(new Coordinate(x, y)).getColor() == currentPlayerColor) {
							return currentExaminatedCoordinate;
						}
					}
				}
			}
			return null;
		}
		
		/**
		 * End of inner class
		 */
	}

	private boolean isKingInCheck(Color kingColor) {

		if (getCurrentPlayerColor() == Color.WHITE) {
			return this.moveValidator.isSpecificFieldUnderAttack(moveValidator.getKingsCoordinates(getCurrentPlayerColor()), Color.BLACK);
		}
		return this.moveValidator.isSpecificFieldUnderAttack(moveValidator.getKingsCoordinates(getCurrentPlayerColor()), Color.WHITE);
	}

	/**This method in invoked always after perfoming VALIDATED move, to check if checkmate/stalemate occured.
	 * @param nextMoveColor
	 * @return
	 */
	private boolean isAnyMoveValid(Color nextMoveColor) {
		return this.moveValidator.isAnyMoveValid();
	}

	private Color getCurrentPlayerColor() {
		if (this.board.getMoveHistory().size() % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	private int findLastNonAttackMoveIndex() {
		int counter = 0;
		int lastNonAttackMoveIndex = 0;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getType() != MoveType.ATTACK) {
				lastNonAttackMoveIndex = counter;
			}
			counter++;
		}

		return lastNonAttackMoveIndex;
	}
}
