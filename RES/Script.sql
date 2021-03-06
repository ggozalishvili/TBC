CREATE TABLE public.client
(
    firstname character varying COLLATE pg_catalog."default",
    lastname character varying COLLATE pg_catalog."default",
    age integer,
    iban character varying COLLATE pg_catalog."default",
    date date
)
    TABLESPACE pg_default;
ALTER TABLE public.client
    OWNER to postgres;

CREATE TABLE public.currency
(
    id integer NOT NULL,
    ccyfrom character varying COLLATE pg_catalog."default",
    ccyto character varying COLLATE pg_catalog."default",
    rate numeric,
    CONSTRAINT currency_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;
ALTER TABLE public.currency
    OWNER to postgres;

CREATE TABLE public.transaction
(
    iban character varying COLLATE pg_catalog."default",
    amount numeric,
    currencyid integer,
    date date
)
    TABLESPACE pg_default;
ALTER TABLE public.transaction
    OWNER to postgres;
