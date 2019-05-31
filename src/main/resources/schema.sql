create table if not exists baconator_cache (id identity, start bigint, end bigint, originalData CLOB(30K));
create table if not exists baconator_history (runId varchar(40), json CLOB(30K));