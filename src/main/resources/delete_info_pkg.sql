-------------------------------------------------------------------

create or replace function arena_info.delete_games(p_games_id int)
  returns text
as $func$
begin
  delete from arena_info.games gg
  where gg.games_id = p_games_id;
  return null;
end;
$func$
LANGUAGE 'plpgsql';
-------------------------------------------------------------------------------------------------------