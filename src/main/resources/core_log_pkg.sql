create or replace function hackatone.hacka.getScenByTagsId(p_scen_tag_id int [])
  returns table(
    id     int,
    tags    text,
    descr   text
  )
as $func$

DECLARE n integer;

begin
if(cardinality(p_scen_tag_id)=1) then
n=0;
else
n=1;
end if;
  return query

select
    s2.id,
    s2.nam,
    s2.descr
    from
    hackatone.hacka.scenario_tags2 s
    join hackatone.hacka.scenario s2 on s.sce_id = s2.id
    where s2.id=any (
    select s2.id
    from hackatone.hacka.scenario_tags2 s
    join hackatone.hacka.scenario s2 on s.sce_id = s2.id
    where s.tag_id = any (
    select t.tag_id from
    hackatone.hacka.tags2 t
    where t.tag_val similar to(
    select
    '%'||string_agg(substring(t2.tag_val from 0 for length(t2.tag_val)), E'|')||'%'
    from
    hackatone.hacka.tags2 t2
    where t2.tag_id = any(p_scen_tag_id)
    )
    )
    GROUP BY s2.id
    having count(s2.id)>n
    order by  count(s2.id) desc
    )

    GROUP BY s2.id
order by  count(s2.id) desc
    ;




-- select
--        s2.id,
--        s2.nam,
--        s2.descr
-- from
--      hackatone.hacka.scenario_tags2 s
--        join hackatone.hacka.scenario s2 on s.sce_id = s2.id
-- where s2.id=any (
--
--                 select s2.id
--               from hackatone.hacka.scenario_tags2 s
--                      join hackatone.hacka.scenario s2 on s.sce_id = s2.id
--               where s.tag_id = any (p_scen_tag_id)
--               GROUP BY s2.id
-- having count(s2.id)>n
-- order by  count(s2.id) desc
--                 )
-- GROUP BY s2.id;



end;
$func$
LANGUAGE plpgsql;

--------------------------------------------------------------

create or replace function hackatone.hacka.getScenByTagsIdOne(p_scen_tag_id int [])
  returns table(
    id     int,
    tags    text,
    descr   text
  )
as $func$
begin
  return query
select
       s2.id,
       s2.nam,
       s2.descr
from
     hackatone.hacka.scenario_tags2 s
       join hackatone.hacka.scenario s2 on s.sce_id = s2.id
where s2.id=any (

                select s2.id
              from hackatone.hacka.scenario_tags2 s
                     join hackatone.hacka.scenario s2 on s.sce_id = s2.id
              where s.tag_id = any (array[1578,1136])
              GROUP BY s2.id
having count(s2.id)>1
order by  count(s2.id) desc
                )
GROUP BY s2.id;



end;
$func$
LANGUAGE plpgsql;
