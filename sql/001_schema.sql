--
-- PostgreSQL database dump
--

\restrict qUKPUfdF1XaZKFyr7OWIV8xItPXHqzd16bj3hHTVh8SfbK7sCcV2bF7xXlJjpVh

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-10-22 21:07:36

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS onboard;
--
-- TOC entry 4938 (class 1262 OID 16388)
-- Name: onboard; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE onboard WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


ALTER DATABASE onboard OWNER TO postgres;

\unrestrict qUKPUfdF1XaZKFyr7OWIV8xItPXHqzd16bj3hHTVh8SfbK7sCcV2bF7xXlJjpVh
\connect onboard
\restrict qUKPUfdF1XaZKFyr7OWIV8xItPXHqzd16bj3hHTVh8SfbK7sCcV2bF7xXlJjpVh

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 223 (class 1259 OID 32828)
-- Name: game; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.game (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    date timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone,
    host_player_id bigint
);


ALTER TABLE public.game OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24582)
-- Name: game_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.game_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.game_seq OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 32836)
-- Name: participation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participation (
    id bigint NOT NULL,
    amount numeric(10,2),
    created_at timestamp(6) without time zone,
    no_flake boolean,
    updated_at timestamp(6) without time zone,
    game_id bigint,
    player_id bigint
);


ALTER TABLE public.participation OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 24595)
-- Name: participation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.participation_seq OWNER TO postgres;

ALTER TABLE participation ALTER COLUMN id SET DEFAULT nextval('participation_seq');
--
-- TOC entry 219 (class 1259 OID 16413)
-- Name: player_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.player_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.player_seq OWNER TO postgres;

ALTER TABLE game ALTER COLUMN id SET DEFAULT nextval('game_seq');

--
-- TOC entry 220 (class 1259 OID 16414)
-- Name: player; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.player (
    id bigint DEFAULT nextval('public.player_seq'::regclass) CONSTRAINT player_id_not_null1 NOT NULL,
    city character varying(255),
    created_at timestamp(6) without time zone,
    email character varying(100) CONSTRAINT player_email_not_null1 NOT NULL,
    full_name character varying(255) CONSTRAINT player_full_name_not_null1 NOT NULL,
    password character varying(255) CONSTRAINT player_password_not_null1 NOT NULL,
    updated_at timestamp(6) without time zone
);


ALTER TABLE public.player OWNER TO postgres;

--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 221
-- Name: game_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.game_seq', 1, false);


--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 222
-- Name: participation_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.participation_seq', 1, false);


--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 219
-- Name: player_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.player_seq', 1, true);


--
-- TOC entry 4772 (class 2606 OID 32835)
-- Name: game game_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);


--
-- TOC entry 4776 (class 2606 OID 32841)
-- Name: participation participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_pkey PRIMARY KEY (id);


--
-- TOC entry 4768 (class 2606 OID 16424)
-- Name: player player_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_pkey1 PRIMARY KEY (id);


--
-- TOC entry 4770 (class 2606 OID 16426)
-- Name: player ukoivbimcon0iqmb8efpv723h08; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT ukoivbimcon0iqmb8efpv723h08 UNIQUE (email);


--
-- TOC entry 4778 (class 2606 OID 32847)
-- Name: participation game_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT game_fkey FOREIGN KEY (game_id) REFERENCES public.game(id);


--
-- TOC entry 4777 (class 2606 OID 32842)
-- Name: game host_player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT host_player_fkey FOREIGN KEY (host_player_id) REFERENCES public.player(id);


--
-- TOC entry 4779 (class 2606 OID 32852)
-- Name: participation player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT player_fkey FOREIGN KEY (player_id) REFERENCES public.player(id);


-- Completed on 2025-10-22 21:07:36

--
-- PostgreSQL database dump complete
--

\unrestrict qUKPUfdF1XaZKFyr7OWIV8xItPXHqzd16bj3hHTVh8SfbK7sCcV2bF7xXlJjpVh

