
create or replace function arena_info.registration_gamer(p_login text, p_password text, p_email text, p_game text)
  returns integer
as $func$
declare
l_r int;
l_game int;
begin

  select
    ag.games_id into l_game
  from arena_info.games ag
  where ag.game_name=p_game;

    IF  l_game is null
    then
      return -2; --same game not found
    END IF;

  select r.registrated_id into l_r
  from arena_info.registrated r
  where r.games_id=l_game and r.login=p_login;

  if l_r is null
    then
      insert into arena_info.registrated (login, password, email,   games_id)
       VALUES (p_login , p_password , p_email ,  l_game);
      return 0;--success registration
    else
      return -3;-- already registration same login
    end if;
end;
$func$
LANGUAGE 'plpgsql';

create or replace function arena_info.authorization_gamer(p_login text, p_password text,p_game text)
  returns table(
  registrated_id int,
  count_kill_monster int,
  count_kill_boss_monster int,
  count_kill_enemy_player int,
  count_death int,
  max_level int,
  team_a int,
  team_b int,
  info text
  )
as $func$
begin
  return query
  select
  r.registrated_id,
  r.count_kill_monster,
  r.count_kill_boss_monster,
  r.count_kill_enemy_player,
  r.count_death,
  r.max_level,
  r.team_a,
  r.team_b,
  r.info
  from arena_info.registrated r
  join arena_info.games g on r.games_id = g.games_id
  where r.login=p_login and r.password=p_password and g.game_name=p_game;
end;
$func$
LANGUAGE 'plpgsql';


create or replace function arena_info.update_gamer(p_registrated_id integer,
p_info text,
p_count_kill_monster integer,
p_count_kill_boss_monster integer,
p_count_kill_enemy_player integer,
p_count_death integer,
p_max_level integer,
p_team_a integer,
p_team_b integer
)
  returns void
as $func$
begin
  update arena_info.registrated set
  info=p_info,
  count_kill_monster=p_count_kill_monster,
count_kill_boss_monster=p_count_kill_boss_monster,
count_kill_enemy_player=p_count_kill_enemy_player,
count_death=p_count_death,
max_level=p_max_level,
team_a=p_team_a,
team_b=p_team_b
  where registrated_id=p_registrated_id;
end;
$func$
LANGUAGE 'plpgsql';

