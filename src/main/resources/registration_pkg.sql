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
      insert into arena_info.registrated (login, password, email, games_id) VALUES (p_login , p_password , p_email ,  l_game);
      return 0;--success registration
    else
      return -3;-- already registration same login
    end if;
end;
$func$
LANGUAGE 'plpgsql';

create or replace function arena_info.authorization_gamer(p_login text, p_password text)
  returns table(
  registrated_id integer,
  info text
  )
as $func$
begin
  return query
  select
  r.registrated_id,
  r.info
  from arena_info.registrated r
  where r.login=p_login and r.password=p_password;
end;
$func$
LANGUAGE 'plpgsql';


create or replace function arena_info.update_gamer(p_registrated_id integer, p_info text)
  returns void
as $func$
begin
  update arena_info.registrated set info=p_info
  where registrated_id=p_registrated_id;
end;
$func$
LANGUAGE 'plpgsql';

