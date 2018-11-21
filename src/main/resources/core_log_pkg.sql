create or replace function arena_info.insert_array_info_macAddres(p_game_start        text [], p_game_end text [],
                                                                 p_play_start        text [],
                                                                 p_player_count      int [], p_server_ip text [],
                                                                 p_game_name         text [], p_info text [],
                                                                 p_server_mac_addres text)
  returns void
as $func$
begin
  insert into arena_info.log_run_game (game_name,
                                      info,
                                      game_start,
                                      game_end,
                                      play_start,
                                      player_count,
                                      server_ip,
                                      mac_addr)
  select unnest(p_game_name),
         unnest(p_info),
         unnest(p_game_start) :: timestamp,
         unnest(p_game_end),
         unnest(p_play_start) :: timestamp,
         unnest(p_player_count),
         unnest(p_server_ip),
         p_server_mac_addres;
end;
$func$
LANGUAGE plpgsql;

--------------------------------------------------------------
create or replace function arena_info.insert_array_crash_info_macAddres(p_game_start        text [], p_player_ip text [],
                                                                       p_server_ip         text [], p_log_sever text [],
                                                                       p_log_player        text [], p_name text [],
                                                                       p_server_mac_addres text)
  returns void
as $func$
begin
  insert into arena_info.log_crash_game (game_start, player_ip, server_ip, log_sever, log_player, game_name, mac_addr)
  select unnest(p_game_start) :: timestamp,
         unnest(p_player_ip),
         unnest(p_server_ip),
         unnest(p_log_sever),
         unnest(p_log_player),
         unnest(p_name),
         p_server_mac_addres;
end;
$func$
LANGUAGE plpgsql;

--------------------------------------------------------------

create or replace function arena_info.get_log_game(p_name          text, p_date_begin text, p_date_end text,                                             p_game_id int)
  returns table(
    mac_addr     text,
    game_name    text,
    game_start   text,
    play_start   text,
    game_end     text,
    player_count int,
    server_ip    text,
    place_game    text
  )
as $func$
begin
  return query
  select
      --   distinct
         lrg.mac_addr,
         lrg.game_name,
         to_char(lrg.game_start, 'DD.MM.YYYY  HH24:MI:SS'),
         to_char(lrg.play_start, 'DD.MM.YYYY  HH24:MI:SS'),
         lrg.game_end,
         lrg.player_count,
         lrg.server_ip,
         lrg.info as place_game
  from arena_info.init_contractor_game_mac_key icgmk
         join arena_info.contractor_games cg on icgmk.contractor_games_id = cg.contractor_games_id
         join arena_info.games gg on cg.games_id = gg.games_id
         join arena_info.contractor gc on cg.contractor_id = gc.contractor_id
         join arena_info.log_run_game lrg on lrg.mac_addr = icgmk.mac_address
  where 1 = 1
    and (cg.contractor_id = p_contractor_id or p_contractor_id = 0)
    and (cg.games_id = p_game_id or p_game_id = 0)
    and upper(lrg.game_name) = upper(gg.game_name)
    and (upper(gg.game_name) = upper(p_name) or p_name isnull or p_name = '')
    and (lrg.game_start :: date between to_date(p_date_begin, 'YYYY-MM-DD') and to_date(p_date_end, 'YYYY-MM-DD')
           or p_date_begin = '' or p_date_end = '')
  order by lrg.game_start;
end;
$func$
LANGUAGE 'plpgsql';

--------------------------------------------------------------
create or replace function arena_info.get_crash_game(p_name          text, p_date_begin text, p_date_end text,
                                                    p_contractor_id int, p_game_id int)
  returns table(
    game_name  varchar(30),
    game_date  text,
    player_ip  text,
    server_ip  text,
    log_player text,
    log_server text,
    mac_addr   text
  )
as $func$
begin
  return query
  select distinct g.game_name,
                  to_char(g.game_start, 'DD Mon YYYY  HH24:MI:SS'),
                  g.player_ip,
                  g.server_ip,
                  g.log_player,
                  g.log_sever,
                  g.mac_addr
  from arena_info.log_crash_game g
         left join arena_info.contractor_games cg
           on (cg.contractor_id = p_contractor_id or p_contractor_id isnull or p_contractor_id = 0) and
              (cg.games_id = p_game_id or p_game_id isnull or p_game_id = 0)
         left join arena_info.init_contractor_game_mac_key icgmk on cg.contractor_games_id = icgmk.contractor_games_id
  where (g.game_name = p_name or p_name isnull or p_name = '')
    and (icgmk.mac_address = g.mac_addr or (p_contractor_id = 0 and p_game_id = 0))
    and (g.game_start :: date between to_date(p_date_begin, 'YYYY-MM-DD') and to_date(p_date_end, 'YYYY-MM-DD')
           or p_date_begin = '' or p_date_end = '');
end;
$func$
LANGUAGE 'plpgsql';