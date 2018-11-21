create or replace function arena_info.get_gamers_game(p_games_id int)
   returns table(
   registrated_id int,
  login text,
  password text,
  email text,
  registrate_date text,
  info text,
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
  r.info,
  g.game_name
  from arena_info.registrated r
  join arena_info.games g on r.games_id = g.games_id
  where r.games_id=p_games_id or p_games_id=0;
end;
$func$
LANGUAGE 'plpgsql';


