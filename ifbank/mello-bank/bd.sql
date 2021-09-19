-- Table: public.registros

-- DROP TABLE public.registros;

CREATE TABLE IF NOT EXISTS public.registros
(
    id integer NOT NULL DEFAULT nextval('registros_id_registro_seq'::regclass),
    user_id integer NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    valor numeric(7,2),
    tipo character varying(10) COLLATE pg_catalog."default",
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT registros_pkey PRIMARY KEY (id),
    CONSTRAINT registros_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.registros
    OWNER to postgres;

-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    data_cadastro timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;