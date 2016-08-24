package com.capgemini.chess.algorithms.data.enums;

/**
 * Types of moves
 * 
 * @author Michal Bejm
 * @Przemek - @ATTACK move is probably a normal move, without capturing second player's Figure
 * @Przemek - @CAPTURE - taking down another player's figure
 * @Przemek - @CASTLING - King and Rook(wieża) involved - 
 * very good explanation on http://mekk.waw.pl/mk/szachy/slownik/podstawy#ruchy
 * @Przemek - @EN_PASSANT - this move consider only pawns(pionków) - very good explanation on 
 * http://mekk.waw.pl/mk/szachy/slownik/podstawy#ruchy
 */
public enum MoveType {
	ATTACK,
	CAPTURE,
	CASTLING,
	EN_PASSANT;
}
