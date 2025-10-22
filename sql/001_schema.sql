DROP TABLE IF EXISTS game CASCADE;
DROP TABLE IF EXISTS participation CASCADE;
DROP TABLE IF EXISTS player CASCADE;
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
    INCREMENT BY 1
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
    INCREMENT BY 1
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
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.player_seq OWNER TO postgres;

ALTER TABLE player
ALTER COLUMN id SET DEFAULT nextval('player_seq');

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


