--
-- PostgreSQL database dump
--

-- Dumped from database version 13.21
-- Dumped by pg_dump version 13.21

-- Started on 2025-06-15 15:26:52

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16397)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id integer NOT NULL,
    nome character varying(50) NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16395)
-- Name: categoria_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_id_seq OWNER TO postgres;

--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_id_seq OWNED BY public.categoria.id;


--
-- TOC entry 205 (class 1259 OID 16421)
-- Name: despesa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.despesa (
    id integer NOT NULL,
    descricao character varying(100) NOT NULL,
    valor numeric(10,2) NOT NULL,
    data date NOT NULL,
    categoria_id integer,
    CONSTRAINT despesa_valor_check CHECK ((valor > (0)::numeric))
);


ALTER TABLE public.despesa OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16419)
-- Name: despesa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.despesa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.despesa_id_seq OWNER TO postgres;

--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 204
-- Name: despesa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.despesa_id_seq OWNED BY public.despesa.id;


--
-- TOC entry 203 (class 1259 OID 16407)
-- Name: receita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receita (
    id integer NOT NULL,
    descricao character varying(100) NOT NULL,
    valor numeric(10,2) NOT NULL,
    data date NOT NULL,
    categoria_id integer,
    CONSTRAINT receita_valor_check CHECK ((valor > (0)::numeric))
);


ALTER TABLE public.receita OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16405)
-- Name: receita_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receita_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.receita_id_seq OWNER TO postgres;

--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 202
-- Name: receita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receita_id_seq OWNED BY public.receita.id;


--
-- TOC entry 2862 (class 2604 OID 16400)
-- Name: categoria id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id SET DEFAULT nextval('public.categoria_id_seq'::regclass);


--
-- TOC entry 2865 (class 2604 OID 16424)
-- Name: despesa id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.despesa ALTER COLUMN id SET DEFAULT nextval('public.despesa_id_seq'::regclass);


--
-- TOC entry 2863 (class 2604 OID 16410)
-- Name: receita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita ALTER COLUMN id SET DEFAULT nextval('public.receita_id_seq'::regclass);


--
-- TOC entry 3006 (class 0 OID 16397)
-- Dependencies: 201
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoria (id, nome) FROM stdin;
2	Aluguel
4	Lazer
5	Transporte
3	Alimentacao
1	Salario
\.


--
-- TOC entry 3010 (class 0 OID 16421)
-- Dependencies: 205
-- Data for Name: despesa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.despesa (id, descricao, valor, data, categoria_id) FROM stdin;
1	Aluguel apartamento	1200.00	2025-06-05	2
2	Mercado	400.00	2025-06-09	3
3	Cinema	70.00	2025-06-10	4
\.


--
-- TOC entry 3008 (class 0 OID 16407)
-- Dependencies: 203
-- Data for Name: receita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receita (id, descricao, valor, data, categoria_id) FROM stdin;
1	Pagamento CLT	3000.00	2025-06-01	1
2	Freelancer site	1500.00	2025-06-08	1
\.


--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 200
-- Name: categoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_id_seq', 5, true);


--
-- TOC entry 3020 (class 0 OID 0)
-- Dependencies: 204
-- Name: despesa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.despesa_id_seq', 3, true);


--
-- TOC entry 3021 (class 0 OID 0)
-- Dependencies: 202
-- Name: receita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receita_id_seq', 2, true);


--
-- TOC entry 2868 (class 2606 OID 16402)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 2872 (class 2606 OID 16427)
-- Name: despesa despesa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_pkey PRIMARY KEY (id);


--
-- TOC entry 2870 (class 2606 OID 16413)
-- Name: receita receita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_pkey PRIMARY KEY (id);


--
-- TOC entry 2874 (class 2606 OID 16428)
-- Name: despesa despesa_categoria_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_categoria_id_fkey FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


--
-- TOC entry 2873 (class 2606 OID 16414)
-- Name: receita receita_categoria_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_categoria_id_fkey FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


-- Completed on 2025-06-15 15:26:52

--
-- PostgreSQL database dump complete
--

