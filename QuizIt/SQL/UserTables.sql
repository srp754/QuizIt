USE c_cs108_ashavsky;
SET @@auto_increment_increment=1;
 
CREATE TABLE IF NOT EXISTS UserDetail 
(
	 UserId int AUTO_INCREMENT,
	 UserName varchar(100),
	 UserPassword varchar(40),
	 UserSalt varchar(40),
	 UserEmail varchar(100),
	 PRIMARY KEY (QuizId)
 );