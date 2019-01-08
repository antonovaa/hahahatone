
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

create or replace function arena_info.get_characters_gamer(p_registrated_id int)  returns table(
  characters_id int,
  character_level int,
  character_type int,
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
  c.characters_id,
  c.character_level,
  c.character_type,
  c.count_kill_monster,
  c.count_kill_boss_monster,
  c.count_kill_enemy_player,
  c.count_death,
  c.max_level,
  c.team_a,
  c.team_b,
  c.info
  from arena_info.characters c
  where c.registrated_id=p_registrated_id;
end;
$func$
LANGUAGE 'plpgsql';

create or replace function arena_info.get_id_authorization_gamer(p_login text, p_password text,p_game text)
  returns int
as $func$
declare
l_id int;
begin
  select
  r.registrated_id into l_id
  from arena_info.registrated r
  join arena_info.games g on r.games_id = g.games_id
  where r.login=p_login and r.password=p_password and g.game_name=p_game;
  if l_id is null
  then
  return -1;
  else
  return l_id;
  end if;
end;
$func$
LANGUAGE 'plpgsql';


create or replace function arena_info.update_character(
p_registrated_id int,
p_characters_id integer,
p_character_level integer,
p_count_kill_monster integer,
p_count_kill_boss_monster integer,
p_count_kill_enemy_player integer,
p_count_death integer,
p_max_level integer,
p_team_a integer,
p_team_b integer,
p_info text,
p_additionally BOOLEAN
)
  returns void
as $func$
begin
if p_additionally
then
  update arena_info.characters set
  character_level=p_character_level,
  count_kill_monster=count_kill_monster+p_count_kill_monster,
  count_kill_boss_monster=count_kill_boss_monster+p_count_kill_boss_monster,
  count_kill_enemy_player=count_kill_enemy_player+p_count_kill_enemy_player,
  count_death=count_death+p_count_death,
  max_level=p_max_level,
  team_a=team_a+p_team_a,
  team_b=team_b+p_team_b,
  info=p_info
  where characters_id=p_characters_id and registrated_id=p_registrated_id;
  else
    update arena_info.characters set
  character_level=p_character_level,
  count_kill_monster=p_count_kill_monster,
  count_kill_boss_monster=p_count_kill_boss_monster,
  count_kill_enemy_player=p_count_kill_enemy_player,
  count_death=p_count_death,
  max_level=p_max_level,
  team_a=p_team_a,
  team_b=p_team_b,
  info=p_info
  where characters_id=p_characters_id and registrated_id=p_registrated_id;
  end if;
end;
$func$
LANGUAGE 'plpgsql';



create or replace function arena_info.insert_character(
p_registrated_id int,
p_character_level integer,
p_character_type integer,
p_count_kill_monster integer,
p_count_kill_boss_monster integer,
p_count_kill_enemy_player integer,
p_count_death integer,
p_max_level integer,
p_team_a integer,
p_team_b integer,
p_info text
)
  returns int
as $func$
declare
id_val int;
begin
insert into arena_info.characters ( character_type, count_kill_monster, count_kill_boss_monster, count_kill_enemy_player, count_death, max_level, team_a, team_b, registrated_id, character_level, info)
VALUES (
p_character_type,
p_count_kill_monster,
p_count_kill_boss_monster,
p_count_kill_enemy_player,
p_count_death,
p_max_level,
p_team_a,
p_team_b,
p_registrated_id,
p_character_level,
p_info
)
RETURNING characters_id INTO id_val;
RETURN id_val;
end;
$func$
LANGUAGE 'plpgsql';

