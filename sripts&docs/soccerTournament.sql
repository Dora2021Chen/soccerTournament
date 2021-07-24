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
*/


#--the country list related to the tournaments
create table country(
    id   int          auto_increment,
	name varchar(100) not null,
	constraint pk_country primary key (id),
	constraint uq_country_name unique (name) 
);

create table tournament(
    id        int          auto_increment,
	name      varchar(100) not null,
	countryId int          not null,
	startDate datetime     not null,
	endDate   datetime     not null,
	constraint pk_tournament primary key (id),
	constraint uq_tournament_name unique (name) ,
	constraint fk_tournament_countryId foreign key (countryId) references country(id),
	constraint ck_tournament_date check (endDate>startDate)
);


create table person (
    id           int         auto_increment,
	name         varchar(50) not null,
	role         tinyint     not null,   #--0, player, 1, coach, 2, referee, ...
	countryId    int         not null,
	tournamentId int         not null,
	constraint pk_person primary key (id),
	constraint fk_person_countryId foreign key (countryId) references country(id),
	constraint fk_person_tournamentId foreign key (tournamentId) references tournament(id),
	constraint ck_person_role check (role in (1,2,3))
);


create table team (
    id           int         auto_increment,
	name         varchar(50) not null,
	countryId    int         not null,
	tournamentId int         not null,
	constraint pk_team primary key (id),
	constraint uq_team_name unique (name),
	constraint fk_team_countryId foreign key (countryId) references country(id),
	constraint fk_team_tournamentId foreign key (tournamentId) references tournament(id)
);

create table team_member(
    id        int     auto_increment,
	teamId    int     not null,
	personId  int     not null,
    constraint pk_team_member primary key (id),
	constraint fk_team_member_teamId foreign key (teamId) references team(id)
	constraint fk_team_member_personId foreign key (personId) references person(id)
);

create table game (
    id           int     auto_increment,
	roundNo      tinyint not null,   #--round number of the tournament
	tournamentId int     not null,
    constraint pk_game primary key (id),
	constraint fk_game_countryId foreign key (countryId) references country(id),
	constraint fk_game_tournamentId foreign key (tournamentId) references tournament(id),
	constraint ck_game_roundNo check (roundNo>=1)
);

#--for each game, there are 2 game members, and only one of it is winner
create table game_member (
    id        int     auto_increment,
	gameId    int     not null,
	teamId    int     not null,
	isWinner  boolean not null default false,
    constraint pk_game_member primary key (id),
	constraint fk_game_member_gameId foreign key (gameId) references game(id),
	constraint fk_game_member_teamId foreign key (teamId) references team(id)
);

