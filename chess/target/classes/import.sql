insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (1,'NEWBIE',0,2,1,1,0);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (2,'NEWBIE',10,5,2,2,1);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (3,'WEAKLING',400,10,5,2,5);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (4,'WEAKLING',500,12,6,3,5);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (5,'BEGINNER',700,35,18,8,5);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (6,'BEGINNER',800,40,20,10,10);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (7,'EXPERIENCED_BEGINNER',1500,70,35,20,15);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (8,'MIDDLEBROW',2609,100,50,25,25);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (9,'EXPERIENCED_MIDDLEBORW',7000,160,100,10,50);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (10,'ADVANCED',12000,200,100,10,70);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (11,'ADVANCED',16800,200,100,10,70);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (12,'PROFESSIONAL',27000,300,200,50,50);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (13,'MASTER',60000,390,300,45,45);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (14,'CHUCK_NORRIS_OF_CHESS',80000,450,390,30,30);

--Those player statistics will be on 0 level with no challenges at all for player profiles with ids: 15, 16, 17!
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (15,'NEWBIE',0,0,0,0,0);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (16,'NEWBIE',0,0,0,0,0);
insert into player_statistics (id, player_level, points_total, games_played, games_won, games_drawn, games_lost) values (17,'NEWBIE',0,0,0,0,0);
--end of section

insert into player_profile (id, email, login, password, id_player_statistics) values (1,'john_walker@chess.com','j_walker','none',1);
insert into player_profile (id, email, login, password, id_player_statistics) values (2,'johny_walkerz@chess.com','j_walkerz','none',2);
insert into player_profile (id, email, login, password, id_player_statistics) values (3,'genowefa_prozna@chess.com','g_prozna','none',3);
insert into player_profile (id, email, login, password, id_player_statistics) values (4,'johnny_walkersss@chess.com','j_walkersss','none',4);
insert into player_profile (id, email, login, password, id_player_statistics) values (5,'johny_bravo@chess.com','j_bravo','none',5);
insert into player_profile (id, email, login, password, id_player_statistics) values (6,'jessica_bravo@chess.com','je_bravo','none',6);
insert into player_profile (id, email, login, password, id_player_statistics) values (7,'barbara_nolife@chess.com','b_nolife','none',7);
insert into player_profile (id, email, login, password, id_player_statistics) values (8,'samantha_painless@chess.com','s_painless','none',8);
insert into player_profile (id, email, login, password, id_player_statistics) values (9,'stanislaw_boczny@chess.com','s_boczny','none',9);
insert into player_profile (id, email, login, password, id_player_statistics) values (10,'bronek_poznanski@chess.com','b_poznanski','none',10);
insert into player_profile (id, email, login, password, id_player_statistics) values (11,'sylwia_apoznanska@chess.com','s_apoznanska','none',11);
insert into player_profile (id, email, login, password, id_player_statistics) values (12,'abraham_abrahamski@chess.com','a_abrahamski','none',12);
insert into player_profile (id, email, login, password, id_player_statistics) values (13,'harry_bob@chess.com','h_bob','none',13);
insert into player_profile (id, email, login, password, id_player_statistics) values (14,'dante_alighieri@chess.com','d_alighieri','none',14);

--Those players will be on 0 level with no challenges at all!
insert into player_profile (id, email, login, password, id_player_statistics) values (15,'danuta_piasek@chess.com','d_piasek','none',15);
insert into player_profile (id, email, login, password, id_player_statistics) values (16,'frank_pfang@chess.com','f_pfang','none',16);
insert into player_profile (id, email, login, password, id_player_statistics) values (17,'mark_ofang@chess.com','m_ofang','none',17);
--end of section

insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (1,1,3,'NEWBIE','WEAKLING');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (2,3,13,'WEAKLING','MASTER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (3,7,2,'EXPERIENCED_BEGINNER','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (4,10,13,'ADVANCED','MASTER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (5,12,5,'PROFESSIONAL','BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (6,6,12,'BEGINNER','PROFESSIONAL');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (7,9,14,'EXPERIENCED_MIDDLEBORW','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (8,5,1,'BEGINNER','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (9,8,12,'MIDDLEBROW','PROFESSIONAL');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (10,1,10,'NEWBIE','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (11,12,6,'PROFESSIONAL','BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (12,13,2,'MASTER','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (13,9,11,'EXPERIENCED_MIDDLEBORW','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (14,13,14,'MASTER','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (15,8,9,'MIDDLEBROW','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (16,2,3,'NEWBIE','WEAKLING');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (17,12,2,'PROFESSIONAL','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (18,10,9,'ADVANCED','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (19,11,12,'ADVANCED','PROFESSIONAL');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (20,9,1,'EXPERIENCED_MIDDLEBORW','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (21,12,14,'PROFESSIONAL','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (22,1,11,'NEWBIE','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (23,14,11,'CHUCK_NORRIS_OF_CHESS','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (24,6,10,'BEGINNER','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (25,10,11,'ADVANCED','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (26,5,9,'BEGINNER','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (27,4,8,'WEAKLING','MIDDLEBROW');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (28,1,12,'NEWBIE','PROFESSIONAL');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (29,5,13,'BEGINNER','MASTER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (30,14,2,'CHUCK_NORRIS_OF_CHESS','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (31,7,3,'EXPERIENCED_BEGINNER','WEAKLING');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (32,8,13,'MIDDLEBROW','MASTER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (33,10,4,'ADVANCED','WEAKLING');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (34,6,13,'BEGINNER','MASTER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (35,10,12,'ADVANCED','PROFESSIONAL');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (36,1,9,'NEWBIE','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (37,8,7,'MIDDLEBROW','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (38,11,7,'ADVANCED','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (39,8,6,'MIDDLEBROW','BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (40,11,8,'ADVANCED','MIDDLEBROW');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (41,13,7,'MASTER','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (42,1,2,'NEWBIE','NEWBIE');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (43,4,14,'WEAKLING','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (44,11,5,'ADVANCED','BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (45,2,7,'NEWBIE','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (46,5,10,'BEGINNER','ADVANCED');
insert into challenges (id, id_player_statistics_challenging, id_player_statistics_challenged, level_player_challenging, level_player_challenged) values (47,9,6,'EXPERIENCED_MIDDLEBORW','BEGINNER');

insert into games (id, id_player_statistics_black_pieces, id_player_statistics_white_pieces) values (1, 5, 7);

insert into moves (id_game_entity, from_x_coordinate, from_y_coordinate, move_type, moved_piece, to_x_coordinate, to_y_coordinate) values (1, 1, 6, "ATTACK", "BLACK_PAWN", 1, 5);