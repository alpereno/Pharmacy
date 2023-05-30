--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

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
-- Name: appuser; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appuser (
    id integer NOT NULL,
    tcidnumber character(11) NOT NULL,
    password character(32) NOT NULL,
    isadmin boolean NOT NULL
);


ALTER TABLE public.appuser OWNER TO postgres;

--
-- Name: appuser_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appuser_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appuser_id_seq OWNER TO postgres;

--
-- Name: appuser_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appuser_id_seq OWNED BY public.appuser.id;


--
-- Name: appuser_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appuser_logs (
    id integer NOT NULL,
    tridno character(11),
    logstring character varying(255),
    "timestamp" timestamp without time zone
);


ALTER TABLE public.appuser_logs OWNER TO postgres;

--
-- Name: appuser_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appuser_logs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appuser_logs_id_seq OWNER TO postgres;

--
-- Name: appuser_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appuser_logs_id_seq OWNED BY public.appuser_logs.id;


--
-- Name: pharmacy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pharmacy (
    name character varying(20) NOT NULL,
    address character varying(50) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.pharmacy OWNER TO postgres;

--
-- Name: pharmacy_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pharmacy_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pharmacy_id_seq OWNER TO postgres;

--
-- Name: pharmacy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pharmacy_id_seq OWNED BY public.pharmacy.id;


--
-- Name: appuser id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appuser ALTER COLUMN id SET DEFAULT nextval('public.appuser_id_seq'::regclass);


--
-- Name: appuser_logs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appuser_logs ALTER COLUMN id SET DEFAULT nextval('public.appuser_logs_id_seq'::regclass);


--
-- Name: pharmacy id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmacy ALTER COLUMN id SET DEFAULT nextval('public.pharmacy_id_seq'::regclass);


--
-- Data for Name: appuser; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appuser (id, tcidnumber, password, isadmin) FROM stdin;
20	12345678912	7815696ecbf1c96e6894b779456d330e	t
21	98765432198	7815696ecbf1c96e6894b779456d330e	f
22	12345678914	7815696ecbf1c96e6894b779456d330e	f
23	12345678996	202cb962ac59075b964b07152d234b70	t
\.


--
-- Data for Name: appuser_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appuser_logs (id, tridno, logstring, "timestamp") FROM stdin;
147	98765432198	get all operation	2023-05-30 13:24:38.896
148	98765432198	get all operation	2023-05-30 13:25:12.498
149	98765432198	select Pharmacy operation	2023-05-30 13:25:17.159
150	98765432198	get all operation	2023-05-30 13:25:22.649
151	98765432198	select Pharmacy operation	2023-05-30 13:25:25.174
152	98765432198	get all operation	2023-05-30 13:28:16.776
153	98765432198	Güneş Eczanesi is selected	2023-05-30 13:28:20.336
154	98765432198	get all operation	2023-05-30 13:28:22.689
155	98765432198	Arda Eczanesi is selected	2023-05-30 13:28:25.286
156	12345678914	get all operation	2023-05-30 13:31:39.513
157	12345678914	Alp Eczanesi is selected	2023-05-30 13:31:46.674
158	12345678914	get all operation	2023-05-30 13:31:52.526
159	12345678914	get all operation	2023-05-30 14:22:12.525
160	12345678914	Deniz Eczanesi is selected	2023-05-30 14:22:19.056
161	98765432198	get all operation	2023-05-30 14:56:24.29
162	98765432198	Kartal Eczanesi is selected	2023-05-30 14:56:30.51
163	98765432198	get all operation	2023-05-30 15:04:37.817
164	98765432198	Inci Eczanesi is selected	2023-05-30 15:04:41.559
\.


--
-- Data for Name: pharmacy; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pharmacy (name, address, id) FROM stdin;
Halk Eczanesi	d mah e cad no:f	3
Güneş Eczanesi	g mah h cad no:i	5
Deniz Eczanesi	x mah y cad. no:z	2
Deva Eczanesi	a mah b cad. no:c	25
Arda Eczanesi	A mah r cad no:d	6
Yeni Eczane	Y mah e cad no:n	7
Eski Eczane	E mah s cad no:k	8
Küçük Eczane	K mah u cad no:c	9
Kartal Eczanesi	K mah 37.sk no:t	23
Aile Eczanesi	A mah i.sk no:l E/Merkez	4
Alp Eczanesi	Yeni Mah. 37.sk No:2	27
Engin Eczanesi	E mah n sk no:7 I/Güngören	29
Inci Eczanesi	Inci Mah 368.sk No:3 K/Merkez	30
Özlem Eczanesi	Ozlem mah 305.sk No:8	31
Kent Eczanesi	Kent mah. Sehir sk. no:7 K/Keskin	33
\.


--
-- Name: appuser_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appuser_id_seq', 23, true);


--
-- Name: appuser_logs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appuser_logs_id_seq', 164, true);


--
-- Name: pharmacy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pharmacy_id_seq', 33, true);


--
-- Name: appuser_logs appuser_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appuser_logs
    ADD CONSTRAINT appuser_logs_pkey PRIMARY KEY (id);


--
-- Name: appuser appuser_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appuser
    ADD CONSTRAINT appuser_pkey PRIMARY KEY (id);


--
-- Name: pharmacy pharmacy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pharmacy
    ADD CONSTRAINT pharmacy_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

