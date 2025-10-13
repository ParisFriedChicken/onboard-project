--
-- PostgreSQL database dump
--

\restrict vuHBhCn4DWKiTyOPYuElKYJJhcTWjVy0rdNz8RClGsvEafX3G7fvPKwHNTymm31

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-10-13 22:02:32

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
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 24576)
-- Name: game; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.game (
    id bigint NOT NULL,
    player_id bigint,
    address character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    date character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    host_player_id bigint
);


ALTER TABLE public.game OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 24582)
-- Name: game_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.game_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.game_seq OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24589)
-- Name: participation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participation (
    id bigint NOT NULL,
    game_id bigint,
    player_id bigint,
    created_at timestamp(6) without time zone,
    no_flake boolean,
    updated_at timestamp(6) without time zone
);


ALTER TABLE public.participation OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24595)
-- Name: participation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participation_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.participation_seq OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16414)
-- Name: player; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.player (
    id bigint CONSTRAINT player_id_not_null1 NOT NULL,
    city character varying(255),
    created_at timestamp(6) without time zone,
    email character varying(100) CONSTRAINT player_email_not_null1 NOT NULL,
    full_name character varying(255) CONSTRAINT player_full_name_not_null1 NOT NULL,
    password character varying(255) CONSTRAINT player_password_not_null1 NOT NULL,
    updated_at timestamp(6) without time zone
);


ALTER TABLE public.player OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16413)
-- Name: player_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.player_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.player_seq OWNER TO postgres;

--
-- TOC entry 4926 (class 0 OID 24576)
-- Dependencies: 221
-- Data for Name: game; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.game (id, player_id, address, created_at, date, updated_at, host_player_id) FROM stdin;
\.


--
-- TOC entry 4928 (class 0 OID 24589)
-- Dependencies: 223
-- Data for Name: participation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.participation (id, game_id, player_id, created_at, no_flake, updated_at) FROM stdin;
\.


--
-- TOC entry 4925 (class 0 OID 16414)
-- Dependencies: 220
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.player (id, city, created_at, email, full_name, password, updated_at) FROM stdin;
107	\N	2025-10-12 21:14:15.371	eg2@gmail.com	Emmanuel Granjean	$2a$10$UMSUC.Wwm/cdxusincXjfOqIfmyETKIjNIjOLLB8jq8pWFbEEMiyW	2025-10-12 21:14:15.371
153	\N	2025-10-12 21:18:04.805	fg@gmail.com	Fabrice Granjean	$2a$10$U5pNtk7ObkigF6IlYg8fmOxbtb.GxjAVHXROdiKo4MWV3S25frvDi	2025-10-12 21:18:04.805
154	\N	2025-10-12 21:18:48.598	dr@gmail.com	Daniel Rutin	$2a$10$mjxjFFX.PPr1CzQwiSU0p.SQrf/efu8LgpWpT4mSQIa.5sECmHvku	2025-10-12 21:18:48.598
155	\N	2025-10-12 21:19:33.224	jd@gmail.com	Jaqueline Daumier	$2a$10$2D8DkgzMYZKl/S5M3npAZewimGYMlk4GZaM1XKSQYdRXVAk7GkZ/2	2025-10-12 21:19:33.224
157	\N	2025-10-12 21:20:20.193	dp@gmail.com	David Palmer	$2a$10$gOSkzXLXxb11JzMjFmqQuuxxWs.fSUMIydVXsxRZ9phXZPQwnNo4q	2025-10-12 21:20:20.193
202	\N	2025-10-12 21:22:16.469	mr@gmail.com	Michelle Rutin	$2a$10$ofVBLFjJkEfelLT66nBLK.8DqkyLSL5WeY6sAe5qhAryeiWcxPNMa	2025-10-12 21:22:16.469
252	New York	2025-10-12 21:24:16.496	sp@gmail.com	Stanislas Pralinos	$2a$10$8KCN.dy64gZHQOU/qbJRNObBEG.TeH3ysoNGkCaCZ4AC0VdQtItRO	2025-10-12 21:24:16.496
\.


--
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 222
-- Name: game_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.game_seq', 1, false);


--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 224
-- Name: participation_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.participation_seq', 1, false);


--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 219
-- Name: player_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.player_seq', 301, true);


--
-- TOC entry 4770 (class 2606 OID 24581)
-- Name: game game_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);


--
-- TOC entry 4772 (class 2606 OID 24594)
-- Name: participation participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_pkey PRIMARY KEY (id);


--
-- TOC entry 4766 (class 2606 OID 16424)
-- Name: player player_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_pkey1 PRIMARY KEY (id);


--
-- TOC entry 4768 (class 2606 OID 16426)
-- Name: player ukoivbimcon0iqmb8efpv723h08; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT ukoivbimcon0iqmb8efpv723h08 UNIQUE (email);


--
-- TOC entry 4773 (class 2606 OID 24583)
-- Name: game fk69kxn13hw2qili6x6em4ur6kd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk69kxn13hw2qili6x6em4ur6kd FOREIGN KEY (player_id) REFERENCES public.player(id);


--
-- TOC entry 4775 (class 2606 OID 24601)
-- Name: participation fka3t5dqw7fywf1e5ln3wmw7h3d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT fka3t5dqw7fywf1e5ln3wmw7h3d FOREIGN KEY (player_id) REFERENCES public.player(id);


--
-- TOC entry 4774 (class 2606 OID 24609)
-- Name: game fklpixk5guer8pa531v7cfuel9s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fklpixk5guer8pa531v7cfuel9s FOREIGN KEY (host_player_id) REFERENCES public.player(id);


--
-- TOC entry 4776 (class 2606 OID 24596)
-- Name: participation fklqo9f5gvkcwq0alysxg6wxlxm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT fklqo9f5gvkcwq0alysxg6wxlxm FOREIGN KEY (game_id) REFERENCES public.game(id);


-- Completed on 2025-10-13 22:02:33

--
-- PostgreSQL database dump complete
--

\unrestrict vuHBhCn4DWKiTyOPYuElKYJJhcTWjVy0rdNz8RClGsvEafX3G7fvPKwHNTymm31

