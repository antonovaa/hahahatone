create or replace function arena_info.get_gamers_game(p_games_id int)
    returns table(
    registrated_id int,
    login text,
    password text,
    email text,
    registrate_date text,
    character_type int,
    count_kill_boss_monster int,
    game_name text
  )
as $func$
begin
  return query
  select
  r.registrated_id,
  r.login,
  r.password,
  r.email,
  to_char(r.registrate_date, 'DD.MM.YYYY'),
  ac.character_type,
  ac.count_kill_boss_monster,
  g.game_name
  from arena_info.registrated r
  join arena_info.games g on r.games_id = g.games_id
  left join arena_info.characters ac on r.registrated_id = ac.registrated_id
  where r.games_id=p_games_id or p_games_id=0;
end;
$func$
LANGUAGE 'plpgsql';


