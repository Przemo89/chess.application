package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;

/**Test class for testing {@link BoardManager}.
 * 
 * @author Michal Bejm
 *
 */

/**TestCase 1. Board initial generator - check if all White and Black pieces are on the right position.
 * TestCase 2. Adding move method with "Attack" move type should actualize board with new position of moved Piece
 * TestCase 3. Adding move method with "Capture" move type should actualize board with 
 * new position of moved Piece and remove the Piece, which was on the destination field.
 * TestCase 4. Proper "Attack" move type of the few pieces on the board should be accepted by the BoardManager.
 * TestCase 5. Invalid "Attack" move type on the board should throw InvalidMoveException.
 * TestCase 6. Proper "Capture" move type (like capturing opponent's pieces) 
 * on the board should be accepted by the BoardManager.
 * TestCase 7. Invalid "Capture" move type (like attempting capturing own pieces) 
 * should throw InvalidMoveException.
 * TestCase 8. Move beyond the board should cause throwing InvalidMoveException.
 * TestCase 9. Attempting to start game as Black player should throw InvalidMoveException.
 * TestCase 10. Attempting to move from field, which is not occupied by any Piece, 
 * should throw InvalidMoveException.
 * TestCase 11. Attempting to move from field, which is occupied by current 
 * Player's Opponent's Piece, should throw InvalidMoveException.
 * TestCase 12. Attempting to move to the same field as source one should throw InvalidMoveException.
 * TestCase 13. Attempting to perform some kind of invalid move 
 * (like going backward using Pawn) should throw InvalidMoveException.
 * Note: There has been made test for each Piece's possible moves in @PieceTypeTest.
 * TestCase 14. Detected collisions during moves of Bishop, 
 * Rook and Queen Pieces should throw InvalidMoveException.
 * TestCase 15. During Knight moves, there shouldn't be any collision detected.
 * TestCase 16. Attempting by the player to perform move, after which 
 * king would be in check, should throw KingInCheckException.
 * TestCase 17. After each move, game status should be updated properly. 
 * TestCase 17A. In case if game may be continued, it should return REGULAR status.
 * TestCase 17B. In case if after first player move, second player's king is in check, 
 * it should return CHECK status.
 * TestCase 17C. In case if after first player move, second player's king is in check, 
 * and there is no possible move to change this state, it should return CHECK_MATE status.
 * TestCase 17D. In case if after first player move, second player's king is not in check, 
 * but there is no possible move to perform, after which King wouldn't be in check,
 * it should return STALE_MATE status.
 * TestCase 18. In case if same position of Pieces happen to be 3 times during one game, 
 * it should be noticed. In different module it'll be handled (game will be over).
 * TestCase 19. In case of game with fifty consequent moves without capturing any piece, 
 * it should be noticed. In different module it'll be handled (game will be over).
 */

public class BoardManagerTest {

	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testGenerateBoardInitialPosition() {
		// given
		List<Move> moves = new ArrayList<>();
		
		// when
		BoardManager boardManager = new BoardManager(moves);
		
		// then
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				if (y > 1 && y < 6) {
					assertNull(boardManager.getBoard().getPieceAt(new Coordinate(x, y)));
				}
				else {
					assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(x, y)));
				}
			}
		}
		assertEquals(Piece.WHITE_PAWN, boardManager.getBoard().getPieceAt(new Coordinate(5, 1)));
		assertEquals(Piece.WHITE_KING, boardManager.getBoard().getPieceAt(new Coordinate(4, 0)));
		assertEquals(Piece.WHITE_BISHOP, boardManager.getBoard().getPieceAt(new Coordinate(5, 0)));
		assertEquals(Piece.BLACK_ROOK, boardManager.getBoard().getPieceAt(new Coordinate(0, 7)));
		assertEquals(Piece.BLACK_KNIGHT, boardManager.getBoard().getPieceAt(new Coordinate(1, 7)));
		assertEquals(Piece.BLACK_QUEEN, boardManager.getBoard().getPieceAt(new Coordinate(3, 7)));
		assertEquals(32, calculateNumberOfPieces(boardManager.getBoard()));
	}
	
	@Test
	public void testGenerateBoardAttack() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(5, 1));
		move.setTo(new Coordinate(5, 3));
		move.setType(MoveType.ATTACK);
		moves.add(move);
		
		// when
		BoardManager boardManager = new BoardManager(moves);
		
		// then
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(5, 1)));
		assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(5, 3)));
		assertEquals(32, calculateNumberOfPieces(boardManager.getBoard()));
	}
	
	@Test
	public void testGenerateBoardCapture() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 6));
		move.setType(MoveType.CAPTURE);
		moves.add(move);
		
		// when
		BoardManager boardManager = new BoardManager(moves);
		
		// then
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(0, 0)));
		assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(0, 6)));
		assertEquals(31, calculateNumberOfPieces(boardManager.getBoard()));
	}
	
	@Test
	public void testGenerateBoardPromotion() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(1, 6));
		move.setTo(new Coordinate(1, 0));
		move.setType(MoveType.CAPTURE);
		moves.add(move);

		// when
		BoardManager boardManager = new BoardManager(moves);
		
		// then
		assertEquals(Piece.BLACK_QUEEN, boardManager.getBoard().getPieceAt(new Coordinate(1, 0)));
	}
	
	@Test
	public void testPerformMoveBishopAttack() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(0, 6));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(0, 6), new Coordinate(6, 0));
		
		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_BISHOP, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMovePawnAttack() {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(4, 6));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(4, 6), new Coordinate(4, 4));
		
		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.BLACK_PAWN, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveKingAttack() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(4, 0), new Coordinate(4, 1));
		
		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_KING, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMoveKnightCapture() {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(3, 4));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(2, 6));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(3, 4), new Coordinate(2, 6));
		
		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.BLACK_KNIGHT, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMoveQueenCapture() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(5, 0));
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(7, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(5, 0), new Coordinate(7, 2));
		
		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.WHITE_QUEEN, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMoveRookCapture() {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(1, 4));
		board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(5, 4));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(1, 4), new Coordinate(5, 4));
		
		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.BLACK_ROOK, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMoveInvalidIndexOutOfBound() {
		// given
		BoardManager boardManager = new BoardManager();
		
		// when
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(8, 6), new Coordinate(7, 6));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	/**This test method determines, if move's order is checked.
	 * In this test there are only White and Black Kings on the board.
	 * Since there wasn't any move, White King should be starting.
	 * However, in this test, Black King is moved first, which should cause an error.
	 */
	@Test
	public void testPerformMoveInvalidMoveOrder() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(0, 7));
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(5, 6));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(0, 7), new Coordinate(1, 6));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidEmptySpot() {
		// given
		Board board = new Board();
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(0, 7), new Coordinate(1, 6));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidSameSpot() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(0, 0));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(0, 0), new Coordinate(0, 0));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	
	@Test
	public void testPerformMoveInvalidPawnBackwardMove() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(1, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 1));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidPawnAttackDestination() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(1, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 2), new Coordinate(0, 3));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidPawnAttackDistance() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(1, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 4));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidPawnCaptureDestination() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(1, 2));
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(1, 3));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 3));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidKingDistance() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(4, 0), new Coordinate(4, 2));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidKnightDestination() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 1));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 1), new Coordinate(3, 3));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidBishopDestination() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(1, 1));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 1), new Coordinate(1, 2));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidQueenLeapsOver() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(1, 1));
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(4, 4));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(1, 1), new Coordinate(6, 6));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidRookLeapsOver() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(3, 0));
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(3, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(3, 0), new Coordinate(3, 7));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveValidKnightLeapsOver() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(3, 2));
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(4, 2));
		board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(3, 3));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boardManager.performMove(new Coordinate(3, 3), new Coordinate(4, 1));
		boolean result = boardManager.getBoard().getPieceAt(new Coordinate(4,1)) == Piece.WHITE_KNIGHT;
			
		// then 
		assertTrue(result);
	}
	
	@Test
	public void testPerformMoveInvalidOwnPieceCapture() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(5, 6));
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(3, 5));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(5, 6), new Coordinate(3, 5));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidKingWouldBeChecked() {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(4, 5));
		board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(4, 7));
		BoardManager boardManager = new BoardManager(board);
		
		// when
		thrown.expect(KingInCheckException.class);
		
		// then 
		boardManager.performMove(new Coordinate(4, 5), new Coordinate(7, 2));
	}
	
	@Test
	public void testUpdateBoardStateRegular() throws InvalidMoveException {
		// given
		BoardManager boardManager = new BoardManager();
		
		// when
		BoardState boardState = boardManager.updateBoardState();
		
		// then
		assertEquals(BoardState.REGULAR, boardState);
	}
	
	@Test
	public void testUpdateBoardStateCheck() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(1, 3));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 0));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		BoardState boardState = boardManager.updateBoardState();
		
		// then
		assertEquals(BoardState.CHECK, boardState);
	}
	
	@Test
	public void testUpdateBoardStateCheckMate() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 1));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(1, 0));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 0));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		BoardState boardState = boardManager.updateBoardState();
		
		// then
		assertEquals(BoardState.CHECK_MATE, boardState);
	}
	
	@Test
	public void testUpdateBoardStateStaleMate() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(7, 0));
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(5, 1));
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(6, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		BoardState boardState = boardManager.updateBoardState();
		
		// then
		assertEquals(BoardState.STALE_MATE, boardState);
	}
	
	@Test
	public void testCheckThreefoldRepetitionRuleSuccessful() {
		// given
		List<Move> moves = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Move move1 = new Move();
			move1.setFrom(new Coordinate(5, 1));
			move1.setTo(new Coordinate(5, 3));
			move1.setType(MoveType.ATTACK);
			moves.add(move1);
			
			Move move2 = new Move();
			move2.setFrom(new Coordinate(5, 6));
			move2.setTo(new Coordinate(5, 4));
			move2.setType(MoveType.ATTACK);
			moves.add(move2);
			
			Move move3 = new Move();
			move3.setFrom(new Coordinate(5, 3));
			move3.setTo(new Coordinate(5, 1));
			move3.setType(MoveType.ATTACK);
			moves.add(move3);
			
			Move move4 = new Move();
			move4.setFrom(new Coordinate(5, 4));
			move4.setTo(new Coordinate(5, 6));
			move4.setType(MoveType.ATTACK);
			moves.add(move4);
		}
		BoardManager boardManager = new BoardManager(moves);
		
		// when
		boolean isThreefoldRepetition = boardManager.checkThreefoldRepetitionRule();
		
		// then
		assertTrue(isThreefoldRepetition);
	}
	
	@Test
	public void testCheckThreefoldRepetitionRuleUnsuccessful() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move1 = new Move();
		move1.setFrom(new Coordinate(5, 1));
		move1.setTo(new Coordinate(5, 3));
		move1.setType(MoveType.ATTACK);
		moves.add(move1);
		
		Move move2 = new Move();
		move2.setFrom(new Coordinate(5, 6));
		move2.setTo(new Coordinate(5, 4));
		move2.setType(MoveType.ATTACK);
		moves.add(move2);
		
		Move move3 = new Move();
		move3.setFrom(new Coordinate(5, 3));
		move3.setTo(new Coordinate(5, 1));
		move3.setType(MoveType.ATTACK);
		moves.add(move3);
		
		Move move4 = new Move();
		move4.setFrom(new Coordinate(5, 4));
		move4.setTo(new Coordinate(5, 6));
		move4.setType(MoveType.ATTACK);
		moves.add(move4);
		BoardManager boardManager = new BoardManager(moves);
		
		// when
		boolean isThreefoldRepetition = boardManager.checkThreefoldRepetitionRule();
		
		// then
		assertFalse(isThreefoldRepetition);
	}
	
	@Test
	public void testCheckFiftyMoveRuleSuccessful() {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		for (int i = 0; i < 100; i++) {
			board.getMoveHistory().add(createDummyMove(board));
		}
			
		// when
		boolean areFiftyMoves = boardManager.checkFiftyMoveRule();
		
		// then
		assertTrue(areFiftyMoves);
	}
	
	@Test
	public void testCheckFiftyMoveRuleUnsuccessfulNotEnoughMoves() {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		for (int i = 0; i < 99; i++) {
			board.getMoveHistory().add(createDummyMove(board));
		}
			
		// when
		boolean areFiftyMoves = boardManager.checkFiftyMoveRule();
		
		// then
		assertFalse(areFiftyMoves);
	}
	
	@Test
	public void testCheckFiftyMoveRuleUnsuccessfulPawnMoved() {
		// given
		BoardManager boardManager = new BoardManager(new Board());
		
		Move move = new Move();
		boardManager.getBoard().setPieceAt(Piece.WHITE_PAWN, new Coordinate(0, 0));
		move.setMovedPiece(Piece.WHITE_PAWN);
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 0));
		move.setType(MoveType.ATTACK);
		boardManager.getBoard().setPieceAt(null, new Coordinate(0, 0));
		boardManager.getBoard().getMoveHistory().add(move);
		
		for (int i = 0; i < 99; i++) {
			boardManager.getBoard().getMoveHistory().add(createDummyMove(boardManager.getBoard()));
		}
			
		// when
		boolean areFiftyMoves = boardManager.checkFiftyMoveRule();
		
		// then
		assertFalse(areFiftyMoves);
	}
	
	private Move createDummyMove(Board board) {
		
		Move move = new Move();
		
		if (board.getMoveHistory().size() % 2 == 0) {
			board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));
			move.setMovedPiece(Piece.WHITE_ROOK);
		}
		else {
			board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 0));
			move.setMovedPiece(Piece.BLACK_ROOK);
		}
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 0));
		move.setType(MoveType.ATTACK);
		board.setPieceAt(null, new Coordinate(0, 0));
		return move;
	}

	private int calculateNumberOfPieces(Board board) {
		int counter = 0;
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				if (board.getPieceAt(new Coordinate(x, y)) != null) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	@Test
	public void shouldReturnCheckMate() {
		//given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(7,7));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(3,0));
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(0,0));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0,4));
		BoardManager test = new BoardManager(board);
		//when
		test.performMove(new Coordinate(0,4), new Coordinate(0,1));
		//then
		assertEquals(BoardState.CHECK_MATE, test.updateBoardState());
	}
	
	@Test
	public void shouldReturnCheck() {
		//given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(7,7));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(3,1));
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(5,7));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0,4));
		BoardManager test = new BoardManager(board);
		//when
		test.performMove(new Coordinate(0,4), new Coordinate(0,1));
		//then
		assertEquals(BoardState.CHECK, test.updateBoardState());
	}
	
	@Test
	public void shouldReturnChecksBecauseBlackRookMayBlockWhiteQueen() {
		//given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(7,7));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(3,0));
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(0,5));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(2,7));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(4,7));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7,1));
		board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(1,1));
		BoardManager test = new BoardManager(board);
		//when
		test.performMove(new Coordinate(0,5), new Coordinate(0,0));
		//then
		assertEquals(BoardState.CHECK, test.updateBoardState());
	}
	
	@Test
	public void shouldReturnCheckBecauseBlackKnightMightBlockWhiteRook() {
		//given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(7,7));
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(4,1));
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(0,1));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7,6));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(6,1));
		board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(4,2));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(4,0));

		BoardManager test = new BoardManager(board);
		//when
		test.performMove(new Coordinate(7,6), new Coordinate(7,0));
		//then
		assertEquals(BoardState.CHECK, test.updateBoardState());
	}
}
