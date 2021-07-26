/*We’d like to see how you do with our coding assignment.
Could you write us a set of classes to model a soccer tournament.
You will need teams, players, and coaches, as well as games and a report of game results for the tournament.

 Acceptance Criteria
 

1. Written in java using object oriented design principles (we’re currently using OpenJDK and Maven for our projects).
2. Provide a basic class diagram showing how you designed your solution
3. Should just be a command line app, no GUI required.
4. Ability to add a team to the tournament
5. Ability to add a coach to a team
6. Ability to add a player to a team
7. Ability to create a game between 2 teams
8. Ability to enter the result of a game
9. Ability to report all game results from a tournament
10.You can persist any information you like using any method you choose.
11.Provide unit tests to test your application (using a framework such as jUnit)


Please complete the assignment and provide a link where the files for your solution can be downloaded (DropBox, google drive or something similar).
If you need any clarification or have any questions don’t hesitate to ask.
If you make any assumptions please let us know what they are.
Aim to have it completed sometime before the end of next week.
 

Thanks,

A Coach will never be a player.
Players and Coaches cannot belong to multiple teams.
I’m currently using 1.8.0.212
*/

/*
drop table game;
drop table person;
drop table team;
*/

create table team (
    id           int         auto_increment,
	name         varchar(50) not null,
	constraint pk_team primary key (id),
	constraint uq_team_name unique (name)
);

create table person (
    id           int         auto_increment,
	name         varchar(50) not null,
	idDocNumber  varchar(50) not null,
	teamId       int         not null,
	role         int         not null,   #--0, player, 1, coach, 2, referee, ...
	constraint pk_person primary key (id),
	constraint fk_person_teamId foreign key (teamId) references team(id),
	constraint uq_person_idDocNumber unique (idDocNumber),
	constraint ck_person_role check (role in (0,1,2))
);

create table game (
    id           int     auto_increment,
	roundNo      int     not null,   #--round number of the tournament
	team1        int     not null,
	team2        int     not null,
	winner       int,
    constraint pk_game primary key (id),
	constraint fk_game_team1 foreign key (team1) references team(id),
	constraint fk_game_team2 foreign key (team2) references team(id),
	constraint uq_game unique (roundNo,team1,team2),
	constraint ck_game_roundNo check (roundNo>=1),
	constraint ck_game_team check (team1<team2),
	constraint ck_game_winner check (winner is null or winner in (team1,team2))
);
