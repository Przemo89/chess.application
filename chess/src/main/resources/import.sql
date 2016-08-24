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

insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (1,1,3,'NEWBIE','WEAKLING');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (2,3,13,'WEAKLING','MASTER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (3,7,2,'EXPERIENCED_BEGINNER','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (4,10,13,'ADVANCED','MASTER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (5,12,5,'PROFESSIONAL','BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (6,6,12,'BEGINNER','PROFESSIONAL');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (7,9,14,'EXPERIENCED_MIDDLEBORW','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (8,5,1,'BEGINNER','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (9,8,12,'MIDDLEBROW','PROFESSIONAL');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (10,1,10,'NEWBIE','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (11,12,6,'PROFESSIONAL','BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (12,13,2,'MASTER','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (13,9,11,'EXPERIENCED_MIDDLEBORW','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (14,13,14,'MASTER','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (15,8,9,'MIDDLEBROW','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (16,2,3,'NEWBIE','WEAKLING');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (17,12,2,'PROFESSIONAL','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (18,10,9,'ADVANCED','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (19,11,12,'ADVANCED','PROFESSIONAL');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (20,9,1,'EXPERIENCED_MIDDLEBORW','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (21,12,14,'PROFESSIONAL','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (22,1,11,'NEWBIE','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (23,14,11,'CHUCK_NORRIS_OF_CHESS','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (24,6,10,'BEGINNER','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (25,10,11,'ADVANCED','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (26,5,9,'BEGINNER','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (27,4,8,'WEAKLING','MIDDLEBROW');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (28,1,12,'NEWBIE','PROFESSIONAL');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (29,5,13,'BEGINNER','MASTER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (30,14,2,'CHUCK_NORRIS_OF_CHESS','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (31,7,3,'EXPERIENCED_BEGINNER','WEAKLING');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (32,8,13,'MIDDLEBROW','MASTER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (33,10,4,'ADVANCED','WEAKLING');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (34,6,13,'BEGINNER','MASTER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (35,10,12,'ADVANCED','PROFESSIONAL');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (36,1,9,'NEWBIE','EXPERIENCED_MIDDLEBORW');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (37,8,7,'MIDDLEBROW','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (38,11,7,'ADVANCED','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (39,8,6,'MIDDLEBROW','BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (40,11,8,'ADVANCED','MIDDLEBROW');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (41,13,7,'MASTER','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (42,1,2,'NEWBIE','NEWBIE');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (43,4,14,'WEAKLING','CHUCK_NORRIS_OF_CHESS');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (44,11,5,'ADVANCED','BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (45,2,7,'NEWBIE','EXPERIENCED_BEGINNER');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (46,5,10,'BEGINNER','ADVANCED');
insert into challenges (id, id_player_challenging, id_player_challenged, level_player_challenging, level_player_challenged) values (47,9,6,'EXPERIENCED_MIDDLEBORW','BEGINNER');



--insert into book (id, title) values (1, 'Pierwsza książka');, id_player_statistics , 1
--insert into book (id, title) values (2, 'Druga książka');
--insert into book (id, title) values (3, 'Trzecia książka');
--
--insert into author (id, first_name, last_name) values (7, 'Jan', 'Kowalski');
--insert into author (id, first_name, last_name) values (8, 'Zbigniew', 'Nowak');
--insert into author (id, first_name, last_name) values (9, 'Janusz', 'Jankowski');
--
--insert into book_author(book_id, author_id) values (1, 7);
--insert into book_author(book_id, author_id) values (1, 8);
--insert into book_author(book_id, author_id) values (2, 8);
--insert into book_author(book_id, author_id) values (3, 9);
--
--insert into departaments (id, name, email, phone_stationary, phone_mobile) values ('1', 'Human Resources', 'hr@company.com','713444567','670890432');
--insert into departaments (id, name, email, phone_stationary, phone_mobile) values ('2', 'Public relations', 'pr@company.com','713444568','670890433');
--insert into departaments (id, name, email, phone_stationary, phone_mobile) values ('3', 'Research and Development', 'rad@company.com','713444569','670890434');
--insert into departaments (id, name, email, phone_stationary, phone_mobile) values ('4', 'Accounting and Finance', 'af@company.com','713444570','670890435');
--insert into departaments (id, name, email, phone_stationary, phone_mobile) values ('5', 'Production', 'prod@company.com','713444571','670890436');
--insert into departaments (id, name, email, phone_stationary, phone_mobile) values ('6', 'Purchasing', 'pur@company.com','713444572','670890437');
--
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('1','1','John','Walker','56061256439','1956-06-12','john_walker@company.com','713544500','772345321');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('2','1','Johny','Walkerz','84111256439','1984-11-12','johny_walkerz@company.com','713544501','772345322');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('3','3','Genowefa','Prozna','79063056439','1979-06-30','genowefa_prozna@company.com','713544502','772345323');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('4','1','Johnny','Walkersss','49061256439','1949-06-12','johnny_walkersss@company.com','713544503','772345324');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('5','4','Johny','Bravo','99033056439','1999-03-30','johny_bravo@company.com','713544504','772345325');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('6','4','Jessica','Bravo','87022056439','1987-02-20','jessica_bravo@company.com','713544505','772345326');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('7','2','Barbara','Nolife','33056439','2000-03-30','barbara_nolife@company.com','713544506','772345327');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('8','3','Samantha','Painless','76033056637','1976-03-30','samantha_painless@company.com','713544507','772345328');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('9','5','Stanislaw','Boczny','77033086637','1977-03-30','stanislaw_boczny@company.com','713544508','772345329');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('10','5','Bronek','Poznanski','67033056437','1967-03-30','bronek_poznanski@company.com','713544509','772345330');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('11','5','Sylwia','Apoznanska','88011056637','1988-01-10','sylwia_apoznanska@company.com','713544510','772345331');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('12','3','Abraham','Abrahamski','54021054637','1954-02-10','abraham_abrahamski@company.com','713544511','772345332');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('13','6','Harry','Bob','70021054637','1970-02-10','harry_bob@company.com','713544512','772345333');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('14','6','Dante','Alighieri','67021054637','1967-02-10','dante_alighieri@company.com','713544513','772345334');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('15','4','Danuta','Piasek','85021054637','1985-02-10','danuta_piasek@company.com','713544514','772345335');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('16','2','Frank','Pfang','67021054527','1967-02-10','frank_pfang@company.com','713544515','772345336');
--insert into employees (id, id_departament, first_name, last_name, PIN, birth_date, email, phone_stationary, phone_mobile) values('17','6','Mark','oFang','53011712637','1953-01-17','mark_ofang@company.com','713544516','772345337');
--
--insert into projects (id, id_manager, name, is_internal) values ('1', '2', 'Communication', '1');
--insert into projects (id, id_manager, name, is_internal) values ('2', '14', 'Javascript Project', '1');
--insert into projects (id, id_manager, name, is_internal) values ('3', '4', 'Bank System', '1');
--insert into projects (id, id_manager, name, is_internal) values ('4', '7', 'Logistic System', '0');
--insert into projects (id, id_manager, name, is_internal) values ('5', '12', 'Database System', '1');
--insert into projects (id, id_manager, name, is_internal) values ('6', '15', 'Java Project', '0');
--
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (1, '3' , '1', '2001-03-30', '2002-02-26', 'PL', '100.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (2, '3' ,'1', '2002-02-27', '2003-01-23', 'DEV', '200.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (3, '3' ,'5', '2001-01-15', '2005-05-26', 'TCD', '1100.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (4, '3' ,'4', '2003-04-15', '2007-05-26', 'FCD', '1100.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (5, '5' ,'1', '2005-01-30', '2007-08-26', 'FCD', '150.75');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (6, '7' ,'2', '2003-08-30', '2007-08-26', 'DEV', '350.64');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (7, '7' ,'3', '2005-08-30', '2010-08-26', 'PL', '245.90');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (8, '9' ,'3', '2001-01-30', '2010-09-26', 'FCD', '545.90');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (9, '9' ,'3', '2010-09-30', '2015-04-29', 'DEV', '645.90');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (10, '9' ,'6', '2010-10-30', '2012-04-29', 'TCD', '234.56');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (11, '10' ,'5', '2008-10-30', '2012-04-29', 'PL', '1234.56');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (12, '11' ,'4', '2006-10-30', '2012-04-29', 'TCD', '432.56');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (13, '11' ,'4', '2013-10-30', '2015-04-29', 'TCD', '450.56');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (14, '13' ,'1', '2014-03-30', '2015-03-26', 'PL', '250.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, termination_date, employee_function, daily_salary) values (15, '13' ,'5', '2013-03-30', '2016-05-16', 'PL', '370.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, employee_function, daily_salary) values (16, '8' ,'4', '2014-03-30', 'TCD', '380.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, employee_function, daily_salary) values (17, '8' ,'3', '2012-09-02', 'DEV', '350.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, employee_function, daily_salary) values (18, '8' ,'3', '2014-09-02', 'TCD', '350.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, employee_function, daily_salary) values (19, '3' ,'1', '2012-09-02', 'PL', '178.25');
--insert into employees_projects (id, id_employee, id_project, hire_date, employee_function, daily_salary) values (20, '13' ,'3', '2014-03-30', 'TCD', '420.25');