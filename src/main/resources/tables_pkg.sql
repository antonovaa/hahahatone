CREATE TABLE hacka.tags2
(
  tag_id integer NOT NULL,
  tag_typ text COLLATE pg_catalog."default" NOT NULL,
  tag_val text COLLATE pg_catalog."default" NOT NULL,
  CONSTRAINT tags2_pkey PRIMARY KEY (tag_id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE hacka.tags2
  OWNER to postgres;
COMMENT ON TABLE hacka.tags2
IS 'Справочник тэгов (комбинации тип-значение)';


CREATE TABLE hacka.scenario
(
  id integer NOT NULL,
  nam text COLLATE pg_catalog."default" NOT NULL,
  descr text COLLATE pg_catalog."default" NOT NULL,
  CONSTRAINT scenario_pkey PRIMARY KEY (id),
  CONSTRAINT nam1 UNIQUE (nam)

)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE hacka.scenario
  OWNER to postgres;
COMMENT ON TABLE hacka.scenario
IS 'Заголовок сценария для оператора';


CREATE TABLE hacka.scenario_tags2
(
  sce_id integer NOT NULL,
  tag_id integer NOT NULL,
  CONSTRAINT scenario_tags2_pkey PRIMARY KEY (sce_id, tag_id),
  CONSTRAINT tag22_sce_id FOREIGN KEY (sce_id)
  REFERENCES hacka.scenario (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT tag22_tag_id FOREIGN KEY (tag_id)
  REFERENCES hacka.tags2 (tag_id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE hacka.scenario_tags2
  OWNER to postgres;
COMMENT ON TABLE hacka.scenario_tags2
IS 'Присвоение тэгов сценарию';

-- ----------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------



-- ----------------------------------------------------------------------------------------------------------

CREATE TABLE hacka.scenario_step
(
  id integer NOT NULL,
  no integer NOT NULL,
  typ text COLLATE pg_catalog."default" NOT NULL,
  descr text COLLATE pg_catalog."default" NOT NULL,
  button text COLLATE pg_catalog."default",
  CONSTRAINT scenario_step_pkey PRIMARY KEY (id, no),
  CONSTRAINT step_sce_id FOREIGN KEY (id)
  REFERENCES hacka.scenario (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE hacka.scenario_step
  OWNER to postgres;
COMMENT ON TABLE hacka.scenario_step
IS 'Шаги сценария';