package com.capgemini.chess.algorithms.data.enums;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;

/**TestCase 1. White pawn can move 2 fields straight forward if it's first move (should be allowed).
 * TestCase 2. White pawn can move 2 fields straight forward if it's not a first move (shouldn't be allowed).
 * TestCase 3. Black pawn can move 2 fields straight forward if it's first move (should be allowed).
 * TestCase 4. Black pawn can move 2 fields straight forward if it's not a first move (shouldn't be allowed).
 * TestCase 4. Black pawn can't move 2 fields straight forward if it's not a first move (shouldn't be allowed).
 * TestCase 5. Pawn can move 1 field diagonally if it's capture (should be allowed).
 * TestCase 6. Pawn can't move 1 field diagonally if it's capture (shouldn't be allowed).
 * TestCase 7. Pawn can't move 3 field straight forward.
 * TestCase 8. Knight Piece's movement's validation tests.
 * TestCase 9. Bishop Piece's movement's validation tests.
 * TestCase 10. Rook Piece's movement's validation tests.
 * TestCase 11. Queen Piece's movement's validation tests.
 * TestCase 12. King Piece's movement's validation tests.
 * TestCase 13. Pawn Piece possible moves' generator tests.
 * TestCase 14. Knight Piece possible moves' generator tests.
 * TestCase 15. Bishop Piece possible moves' generator tests.
 * TestCase 16. Rook Piece possible moves' generator tests.
 * TestCase 17. Queen Piece possible moves' generator tests.
 * TestCase 18. King Piece possible moves' generator tests.
 */
public class PieceTypeTest {
	
	Board board;
	
	@Before
	public void createBoardObject() {
		board = new Board();
	}
	
	/**TestCase 1. Can White pawn move 2 fields straight forward if it's first move (should be allowed).
	 */
	@Test
	public void shouldReturnTrueAsAFirstWhitePawnMove2FieldsAhead(){
		//given
		Coordinate from = new Coordinate(1, 1);
		Piece pawnPiece = Piece.WHITE_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move properPossibleMove = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,3)).setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.ATTACK);
		
		//when
		boolean isMoveValid = pawnPiece.getType().isMovePossible(properPossibleMove);
		
		Assert.assertTrue(isMoveValid);
	}
	
	/**TestCase 2. Can White pawn move 2 fields straight forward if it's not a first move (shouldn't be allowed).
	 */
	@Test
	public void shouldReturnTrueAsAFirstWhitePawnMove1FieldAhead(){
		//given
		Coordinate from = new Coordinate(1, 1);
		Piece pawnPiece = Piece.WHITE_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move properPossibleMove = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,2)).setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.ATTACK);
		
		//when
		boolean isMoveValid = pawnPiece.getType().isMovePossible(properPossibleMove);
		
		Assert.assertTrue(isMoveValid);
	}
	
	/**TestCase 3. Can Black pawn move 2 fields straight forward if it's first move (should be allowed).
	 */
	@Test
	public void shouldReturnTrueAsAFirstBlackPawnMove2FieldsAhead(){
		//given
		Coordinate from = new Coordinate(1, 6);
		Piece pawnPiece = Piece.BLACK_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move properPossibleMove = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,4)).setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.ATTACK);
		
		//when
		boolean isMoveValid = pawnPiece.getType().isMovePossible(properPossibleMove);
		
		Assert.assertTrue(isMoveValid);
	}
	
	/**TestCase 4. Black pawn can't move 2 fields straight forward if it's not a first move (shouldn't be allowed).
	 */
	@Test
	public void shouldReturnTrueAsAFirstBlackPawnMove1FieldAhead(){
		//given
		Coordinate from = new Coordinate(1, 6);
		Piece pawnPiece = Piece.BLACK_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move properPossibleMove = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,4)).setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.ATTACK);
		
		//when
		boolean isMoveValid = pawnPiece.getType().isMovePossible(properPossibleMove);
		
		Assert.assertTrue(isMoveValid);
	}
	
	/**TestCase 5. Pawn can move 1 field diagonally if it's capture (should be allowed).
	 */
	@Test
	public void shouldReturnTrueAsACaptureTypeMoveBlackPawnMoveFieldDiagonally(){
		//given
		Coordinate from = new Coordinate(1, 6);
		Coordinate to = new Coordinate(2, 5);
		Piece pawnPiece = Piece.BLACK_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move properPossibleMove = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(to)
				.setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.CAPTURE);
		
		//when
		boolean isMoveValid = pawnPiece.getType().isMovePossible(properPossibleMove);
		
		Assert.assertTrue(isMoveValid);
	}
	
	/**TestCase 6. Pawn can't move 1 field diagonally if it's attack move type
	 */
	@Test
	public void shouldReturnFalseAsAnAttackTypeMoveBlackPawnMoveFieldDiagonally(){
		//given
		Coordinate from = new Coordinate(1, 6);
		Coordinate to = new Coordinate(2, 5);
		Piece pawnPiece = Piece.BLACK_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move properPossibleMove = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(to)
				.setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.ATTACK);
		
		//when
		boolean isMoveValid = pawnPiece.getType().isMovePossible(properPossibleMove);
		
		Assert.assertFalse(isMoveValid);
	}
	
	/**TestCase 7. Pawn can't move 3 field straight forward or diagonally.
	 */
	@Test
	public void shouldReturnFalseAsBlackPawnMove3FieldForwardOr2FieldsDiagonally(){
		//given
		Coordinate from = new Coordinate(1, 6);
		Coordinate forwardTo = new Coordinate(2, 5);
		Coordinate diagonalTo = new Coordinate(3, 4);
		Piece pawnPiece = Piece.BLACK_PAWN;
		board.setPieceAt(pawnPiece, from);
		Move inproperMoveOne = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(forwardTo)
				.setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.ATTACK);
		Move inproperMoveTwo = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(diagonalTo)
				.setNewMovedPiece(pawnPiece).setNewMoveType(MoveType.CAPTURE);
		
		//when
		boolean isMoveValidOne = pawnPiece.getType().isMovePossible(inproperMoveOne);
		boolean isMoveValidTwo = pawnPiece.getType().isMovePossible(inproperMoveTwo);
		
		//then
		Assert.assertFalse(isMoveValidOne);
		Assert.assertFalse(isMoveValidTwo);
	}
	
	/**TestCase 8. Knight Piece's movement's validation tests.
	 */
	@Test
	public void shouldPassAllTheAssertsWhenTestingKnightsMovementValidationTests() {
		//given
		Board board = new Board();
		Piece knight = Piece.BLACK_KNIGHT;
		PieceType pieceTypeKnight = PieceType.KNIGHT;
		Coordinate from = new Coordinate(2, 2);
		board.setPieceAt(knight, from);
		Move possibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,4)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,0)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveThree = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,0)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFour = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,3)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFive = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(0,1)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		
		Move impossibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,4)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		Move impossibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,5)).setNewMovedPiece(knight)
				.setNewMoveType(MoveType.ATTACK);
		//when

		boolean shouldReturnTrueForPossibleMoveOne = pieceTypeKnight.isMovePossible(possibleMoveOne);
		boolean shouldReturnTrueForPossibleMoveTwo = pieceTypeKnight.isMovePossible(possibleMoveTwo);
		boolean shouldReturnTrueForPossibleMoveThree = pieceTypeKnight.isMovePossible(possibleMoveThree);
		boolean shouldReturnTrueForPossibleMoveFour = pieceTypeKnight.isMovePossible(possibleMoveFour);
		boolean shouldReturnTrueForPossibleMoveFive = pieceTypeKnight.isMovePossible(possibleMoveFive);
		
		boolean shouldReturnFalseForImossibleMoveOne = pieceTypeKnight.isMovePossible(impossibleMoveOne);
		boolean shouldReturnFalseForImossibleMoveTwo = pieceTypeKnight.isMovePossible(impossibleMoveTwo);
		
		//then
		assertTrue(shouldReturnTrueForPossibleMoveOne);
		assertTrue(shouldReturnTrueForPossibleMoveTwo);
		assertTrue(shouldReturnTrueForPossibleMoveThree);
		assertTrue(shouldReturnTrueForPossibleMoveFour);
		assertTrue(shouldReturnTrueForPossibleMoveFive);
		
		assertFalse(shouldReturnFalseForImossibleMoveOne);
		assertFalse(shouldReturnFalseForImossibleMoveTwo);
	}
	
	/**TestCase 9. Bishop Piece's movement's validation tests.
	 */
	@Test
	public void shouldPassAllTheAssertsWhenTestingBishopMovementValidationTests() {
		//given
		Board board = new Board();
		Piece bishop = Piece.WHITE_BISHOP;
		PieceType pieceTypeBishop = PieceType.BISHOP;
		Coordinate from = new Coordinate(4, 3);
		board.setPieceAt(bishop, from);
		Move possibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,1)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(6,5)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveThree = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,5)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFour = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(1,0)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFive = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(7,6)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		
		Move impossibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,5)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		Move impossibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(6,3)).setNewMovedPiece(bishop)
				.setNewMoveType(MoveType.ATTACK);
		//when

		boolean shouldReturnTrueForPossibleMoveOne = pieceTypeBishop.isMovePossible(possibleMoveOne);
		boolean shouldReturnTrueForPossibleMoveTwo = pieceTypeBishop.isMovePossible(possibleMoveTwo);
		boolean shouldReturnTrueForPossibleMoveThree = pieceTypeBishop.isMovePossible(possibleMoveThree);
		boolean shouldReturnTrueForPossibleMoveFour = pieceTypeBishop.isMovePossible(possibleMoveFour);
		boolean shouldReturnTrueForPossibleMoveFive = pieceTypeBishop.isMovePossible(possibleMoveFive);
		
		boolean shouldReturnFalseForImossibleMoveOne = pieceTypeBishop.isMovePossible(impossibleMoveOne);
		boolean shouldReturnFalseForImossibleMoveTwo = pieceTypeBishop.isMovePossible(impossibleMoveTwo);
		
		//then
		assertTrue(shouldReturnTrueForPossibleMoveOne);
		assertTrue(shouldReturnTrueForPossibleMoveTwo);
		assertTrue(shouldReturnTrueForPossibleMoveThree);
		assertTrue(shouldReturnTrueForPossibleMoveFour);
		assertTrue(shouldReturnTrueForPossibleMoveFive);
		
		assertFalse(shouldReturnFalseForImossibleMoveOne);
		assertFalse(shouldReturnFalseForImossibleMoveTwo);
	}
	
	/**TestCase 10. Rook Piece's movement's validation tests.
	 */
	@Test
	public void shouldPassAllTheAssertsWhenTestingRookMovementValidationTests() {
		//given
		Board board = new Board();
		Piece rook = Piece.BLACK_ROOK;
		PieceType pieceTypeRook = PieceType.ROOK;
		Coordinate from = new Coordinate(4, 3);
		board.setPieceAt(rook, from);
		Move possibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(7,3)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,3)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveThree = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,7)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFour = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,0)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFive = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(0,3)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		
		Move impossibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(6,5)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		Move impossibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,5)).setNewMovedPiece(rook)
				.setNewMoveType(MoveType.ATTACK);
		//when

		boolean shouldReturnTrueForPossibleMoveOne = pieceTypeRook.isMovePossible(possibleMoveOne);
		boolean shouldReturnTrueForPossibleMoveTwo = pieceTypeRook.isMovePossible(possibleMoveTwo);
		boolean shouldReturnTrueForPossibleMoveThree = pieceTypeRook.isMovePossible(possibleMoveThree);
		boolean shouldReturnTrueForPossibleMoveFour = pieceTypeRook.isMovePossible(possibleMoveFour);
		boolean shouldReturnTrueForPossibleMoveFive = pieceTypeRook.isMovePossible(possibleMoveFive);
		
		boolean shouldReturnFalseForImossibleMoveOne = pieceTypeRook.isMovePossible(impossibleMoveOne);
		boolean shouldReturnFalseForImossibleMoveTwo = pieceTypeRook.isMovePossible(impossibleMoveTwo);
		
		//then
		assertTrue(shouldReturnTrueForPossibleMoveOne);
		assertTrue(shouldReturnTrueForPossibleMoveTwo);
		assertTrue(shouldReturnTrueForPossibleMoveThree);
		assertTrue(shouldReturnTrueForPossibleMoveFour);
		assertTrue(shouldReturnTrueForPossibleMoveFive);
		
		assertFalse(shouldReturnFalseForImossibleMoveOne);
		assertFalse(shouldReturnFalseForImossibleMoveTwo);
	}
	
	/**TestCase 11. Queen Piece's movement's validation tests.
	 */
	@Test
	public void shouldPassAllTheAssertsWhenTestingQueensMovementValidationTests() {
		//given
		Board board = new Board();
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(3,0));
		board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(1,2));
		PieceType pieceType = PieceType.QUEEN;
		Move possibleMoveOne = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(3,0)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.CAPTURE);
		Move possibleMoveTwo = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(0,3)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveThree = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(3,4)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFour = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(5,2)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFive = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(6,2)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.ATTACK);
		
		Move impossibleMoveOne = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(2,4)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.ATTACK);
		Move impossibleMoveTwo = new Move().setNewCoordinateFrom(new Coordinate(1, 2))
				.setNewCoordinateTo(new Coordinate(4,1)).setNewMovedPiece(Piece.WHITE_QUEEN)
				.setNewMoveType(MoveType.ATTACK);
		//when

		boolean shouldReturnTrueForPossibleMoveOne = pieceType.isMovePossible(possibleMoveOne);
		boolean shouldReturnTrueForPossibleMoveTwo = pieceType.isMovePossible(possibleMoveTwo);
		boolean shouldReturnTrueForPossibleMoveThree = pieceType.isMovePossible(possibleMoveThree);
		boolean shouldReturnTrueForPossibleMoveFour = pieceType.isMovePossible(possibleMoveFour);
		boolean shouldReturnTrueForPossibleMoveFive = pieceType.isMovePossible(possibleMoveFive);
		
		boolean shouldReturnFalseForImossibleMoveOne = pieceType.isMovePossible(impossibleMoveOne);
		boolean shouldReturnFalseForImossibleMoveTwo = pieceType.isMovePossible(impossibleMoveTwo);
		
		//then
		assertTrue(shouldReturnTrueForPossibleMoveOne);
		assertTrue(shouldReturnTrueForPossibleMoveTwo);
		assertTrue(shouldReturnTrueForPossibleMoveThree);
		assertTrue(shouldReturnTrueForPossibleMoveFour);
		assertTrue(shouldReturnTrueForPossibleMoveFive);
		
		assertFalse(shouldReturnFalseForImossibleMoveOne);
		assertFalse(shouldReturnFalseForImossibleMoveTwo);
	}
	
	/**TestCase 12. King Piece's movement's validation tests.
	 */
	@Test
	public void shouldPassAllTheAssertsWhenTestingKingMovementValidationTests() {
		//given
		Board board = new Board();
		Piece king = Piece.BLACK_KING;
		PieceType pieceTypeKing = PieceType.KING;
		Coordinate from = new Coordinate(4, 3);
		board.setPieceAt(king, from);
		Move possibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,4)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(4,2)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveThree = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,2)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFour = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,4)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		Move possibleMoveFive = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,3)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		
		Move impossibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(6,3)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		Move impossibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,5)).setNewMovedPiece(king)
				.setNewMoveType(MoveType.ATTACK);
		//when

		boolean shouldReturnTrueForPossibleMoveOne = pieceTypeKing.isMovePossible(possibleMoveOne);
		boolean shouldReturnTrueForPossibleMoveTwo = pieceTypeKing.isMovePossible(possibleMoveTwo);
		boolean shouldReturnTrueForPossibleMoveThree = pieceTypeKing.isMovePossible(possibleMoveThree);
		boolean shouldReturnTrueForPossibleMoveFour = pieceTypeKing.isMovePossible(possibleMoveFour);
		boolean shouldReturnTrueForPossibleMoveFive = pieceTypeKing.isMovePossible(possibleMoveFive);
		
		boolean shouldReturnFalseForImossibleMoveOne = pieceTypeKing.isMovePossible(impossibleMoveOne);
		boolean shouldReturnFalseForImossibleMoveTwo = pieceTypeKing.isMovePossible(impossibleMoveTwo);
		
		//then
		assertTrue(shouldReturnTrueForPossibleMoveOne);
		assertTrue(shouldReturnTrueForPossibleMoveTwo);
		assertTrue(shouldReturnTrueForPossibleMoveThree);
		assertTrue(shouldReturnTrueForPossibleMoveFour);
		assertTrue(shouldReturnTrueForPossibleMoveFive);
		
		assertFalse(shouldReturnFalseForImossibleMoveOne);
		assertFalse(shouldReturnFalseForImossibleMoveTwo);
	}
	
	/**TestCase 13. Pawn Piece possible moves' generator tests.
	 */
	@Test
	public void shouldReturnArrayListWith3SpecifiedElementsWhenTestingPawnsPossibleMovesGenerator(){
		//given
		PieceType piece = PieceType.PAWN;
		List<Move> properResultsList = new ArrayList<>();
		Coordinate from = new Coordinate(1, 1);
		Piece piecer = Piece.WHITE_PAWN;
		Move properPossibleMoveOne = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,2)).setNewMovedPiece(piecer).setNewMoveType(MoveType.CAPTURE);
		Move properPossibleMoveTwo = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,2)).setNewMovedPiece(piecer).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveThree = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,2)).setNewMovedPiece(piecer).setNewMoveType(MoveType.CAPTURE);
		properResultsList.add(properPossibleMoveOne);
		properResultsList.add(properPossibleMoveTwo);
		properResultsList.add(properPossibleMoveThree);
		
		//when
		List<Move> actualResultList = piece.possibleMoveGenerator(from, piecer);
		boolean isTheSameSize = properResultsList.size() == actualResultList.size();
		
		Assert.assertEquals(properResultsList, actualResultList);
		Assert.assertTrue(isTheSameSize);
	}
	
	/**TestCase 14. Knight Piece possible moves' generator tests.
	 */
	@Test
	public void shouldReturnArrayListWith8SpecifiedElementsWhenTestingKnightGeneratingMoves(){
		//given
		PieceType pieceType = PieceType.KNIGHT;
		List<Move> properResultsList = new ArrayList<>();

		Coordinate from = new Coordinate(3, 3);
		Piece piece = Piece.WHITE_KNIGHT;
		Move properPossibleMoveOne = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(4,5)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveTwo = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(4,1)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveThree = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,5)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFour = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,1)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFive = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(5,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSix = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(5,4)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSeven = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,4)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveEight = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		properResultsList.add(properPossibleMoveOne);
		properResultsList.add(properPossibleMoveTwo);
		properResultsList.add(properPossibleMoveThree);
		properResultsList.add(properPossibleMoveFour);
		properResultsList.add(properPossibleMoveFive);
		properResultsList.add(properPossibleMoveSix);
		properResultsList.add(properPossibleMoveSeven);
		properResultsList.add(properPossibleMoveEight);
		
		//when
		List<Move> actualResultList = pieceType.possibleMoveGenerator(from, piece);
		boolean isTheSameSize = properResultsList.size() == actualResultList.size();
		
		Assert.assertTrue(actualResultList.containsAll(properResultsList));
		Assert.assertTrue(isTheSameSize);
	}
	
	/**TestCase 15. Bishop Piece possible moves' generator tests.
	 */
	@Test
	public void shouldReturnArrayListWith13SpecifiedElementsWhenTestingBishopGeneratingMoves() {
		//given
		PieceType pieceType = PieceType.BISHOP;
		List<Move> properPartialResultsList = new ArrayList<>();

		Coordinate from = new Coordinate(3, 3);
		Piece piece = Piece.BLACK_BISHOP;
		Move properPossibleMoveOne = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,0)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveTwo = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(7,7)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveThree = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(6,0)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFour = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,6)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFive = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,4)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSix = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(5,5)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSeven = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(5,1)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveEight = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,5)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		properPartialResultsList.add(properPossibleMoveOne);
		properPartialResultsList.add(properPossibleMoveTwo);
		properPartialResultsList.add(properPossibleMoveThree);
		properPartialResultsList.add(properPossibleMoveFour);
		properPartialResultsList.add(properPossibleMoveFive);
		properPartialResultsList.add(properPossibleMoveSix);
		properPartialResultsList.add(properPossibleMoveSeven);
		properPartialResultsList.add(properPossibleMoveEight);
		
		//when
		List<Move> actualResultList = pieceType.possibleMoveGenerator(from, piece);
		boolean isTheSameSize = actualResultList.size() == 13;
		
		//then
		Assert.assertTrue(actualResultList.containsAll(properPartialResultsList));
		Assert.assertTrue(isTheSameSize);
	}
	
	/**TestCase 16. Rook Piece possible moves' generator tests.
	 */
	@Test
	public void shouldReturnArrayListWith14SpecifiedElementsWhenTestingRookGeneratingMoves() {
		//given
		PieceType pieceType = PieceType.ROOK;
		List<Move> properPartialResultsList = new ArrayList<>();

		Coordinate from = new Coordinate(3, 3);
		Piece piece = Piece.BLACK_ROOK;
		Move properPossibleMoveOne = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,0)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveTwo = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,7)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveThree = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(7,3)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFour = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(5,3)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFive = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(1,3)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSix = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(0,3)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSeven = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(2,3)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveEight = new Move().setNewCoordinateFrom(from)
				.setNewCoordinateTo(new Coordinate(3,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		properPartialResultsList.add(properPossibleMoveOne);
		properPartialResultsList.add(properPossibleMoveTwo);
		properPartialResultsList.add(properPossibleMoveThree);
		properPartialResultsList.add(properPossibleMoveFour);
		properPartialResultsList.add(properPossibleMoveFive);
		properPartialResultsList.add(properPossibleMoveSix);
		properPartialResultsList.add(properPossibleMoveSeven);
		properPartialResultsList.add(properPossibleMoveEight);
		
		//when
		List<Move> actualResultList = pieceType.possibleMoveGenerator(from, piece);
		boolean isTheSameSize = actualResultList.size() == 14;
		
		//then
		Assert.assertTrue(actualResultList.containsAll(properPartialResultsList));
		Assert.assertTrue(isTheSameSize);
	}
	
	/**TestCase 17. Queen Piece possible moves' generator tests.
	 */
	@Test
	public void shouldReturnArrayListWith23SpecifiedElementsWhenTestingQueenGeneratingMoves() {
		//given
		PieceType pieceType = PieceType.QUEEN;
		List<Move> properPartialResultsList = new ArrayList<>();

		Coordinate from = new Coordinate(1, 6);
		Piece piece = Piece.BLACK_QUEEN;
		Move properPossibleMoveOne = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,6)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveTwo = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveThree = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,7)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFour = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(3,6)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFive = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(6,6)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSix = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(7,6)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSeven = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,7)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveEight = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(3,4)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		properPartialResultsList.add(properPossibleMoveOne);
		properPartialResultsList.add(properPossibleMoveTwo);
		properPartialResultsList.add(properPossibleMoveThree);
		properPartialResultsList.add(properPossibleMoveFour);
		properPartialResultsList.add(properPossibleMoveFive);
		properPartialResultsList.add(properPossibleMoveSix);
		properPartialResultsList.add(properPossibleMoveSeven);
		properPartialResultsList.add(properPossibleMoveEight);
		
		//when
		List<Move> actualResultList = pieceType.possibleMoveGenerator(from, piece);
		boolean isTheSameSize = actualResultList.size() == 23;
		
		//then
		Assert.assertTrue(actualResultList.containsAll(properPartialResultsList));
		Assert.assertTrue(isTheSameSize);
	}
	
	/**TestCase 18. King Piece possible moves' generator tests.
	 */
	@Test
	public void shouldReturnArrayListWith8SpecifiedElementsWhenTestingKingGeneratingMoves() {
		//given
		PieceType pieceType = PieceType.KING;
		List<Move> properPartialResultsList = new ArrayList<>();

		Coordinate from = new Coordinate(1, 1);
		Piece piece = Piece.BLACK_KING;
		Move properPossibleMoveOne = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,0)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveTwo = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,1)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveThree = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(0,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFour = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,0)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveFive = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(1,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSix = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,0)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveSeven = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,1)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		Move properPossibleMoveEight = new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(2,2)).setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK);
		properPartialResultsList.add(properPossibleMoveOne);
		properPartialResultsList.add(properPossibleMoveTwo);
		properPartialResultsList.add(properPossibleMoveThree);
		properPartialResultsList.add(properPossibleMoveFour);
		properPartialResultsList.add(properPossibleMoveFive);
		properPartialResultsList.add(properPossibleMoveSix);
		properPartialResultsList.add(properPossibleMoveSeven);
		properPartialResultsList.add(properPossibleMoveEight);
		
		//when
		List<Move> actualResultList = pieceType.possibleMoveGenerator(from, piece);
		boolean isTheSameSize = actualResultList.size() == 8;
		
		//then

		Assert.assertTrue(actualResultList.containsAll(properPartialResultsList));
		Assert.assertTrue(isTheSameSize);
	}
}
