CREATE SCHEMA "arena_info";

--------------------------------------------------------------

create table if not exists arena_info.games
(
  games_id  serial primary key,
  game_name text
);


--------------------------------------------------------------


create table if not exists arena_info.registrated
(
  registrated_id serial primary key,
  registrate_date        timestamp default now(),
  login         text,
  password         text,
  email         text,
  info text,
  games_id INTEGER REFERENCES arena_info.games (games_id)
);

--------------------------------------------------------------

create table if not exists arena_info.log_crash_game
(
  log_crash_game_id serial not null,
  game_start        timestamp,
  player_ip         text,
  server_ip         text,
  log_sever         text,
  log_player        text,
  game_name         varchar(30),
  mac_addr          text
);

comment on table arena_info.log_crash_game
is 'Crash table';

comment on column arena_info.log_crash_game.game_start
is 'время начала игры';

comment on column arena_info.log_crash_game.player_ip
is 'registrated_id Player';

comment on column arena_info.log_crash_game.server_ip
is 'registrated_id Server';

comment on column arena_info.log_crash_game.log_sever
is 'log server';

comment on column arena_info.log_crash_game.log_player
is 'log client';

--------------------------------------------------------------

create table if not exists arena_info.log_run_game
(
  log_run_game_id serial not null
    constraint log_run_game_pkey
    primary key,
  game_name       text,
  game_start      timestamp,
  game_end        text,
  play_start      timestamp,
  player_count    integer,
  server_ip       text,
  mac_addr        text,
  info            text
);

comment on column arena_info.log_run_game.game_name
is 'имя сервера';

comment on column arena_info.log_run_game.game_start
is 'время старта игры';

comment on column arena_info.log_run_game.game_end
is 'конец игры';

comment on column arena_info.log_run_game.play_start
is 'время начала игры';

comment on column arena_info.log_run_game.info
is 'любая информация в игре';

comment on column arena_info.log_run_game.player_count
is 'количество игроков';

comment on column arena_info.log_run_game.server_ip
is 'registrated_id сервера с игрой';
--------------------------------------------------------------
