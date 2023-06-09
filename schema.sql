PGDMP         4                 {           timetablecss    14.7    14.7                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16468    timetablecss    DATABASE     k   CREATE DATABASE timetablecss WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Romanian_Romania.1252';
    DROP DATABASE timetablecss;
                postgres    false            �            1259    16469 
   discipline    TABLE     �   CREATE TABLE public.discipline (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    year integer NOT NULL
);
    DROP TABLE public.discipline;
       public         heap    postgres    false            �            1259    16472    discipline_id_seq    SEQUENCE     �   ALTER TABLE public.discipline ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.discipline_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    209            �            1259    16493 	   examtable    TABLE     �  CREATE TABLE public.examtable (
    room character varying(8) NOT NULL,
    start_hour integer NOT NULL,
    end_hour integer NOT NULL,
    date character varying(16) NOT NULL,
    course character varying(64) NOT NULL,
    course_type character varying(32) NOT NULL,
    group_name character varying(16) NOT NULL,
    teacher character varying(128) NOT NULL,
    id integer NOT NULL
);
    DROP TABLE public.examtable;
       public         heap    postgres    false            �            1259    16498    examtable_id_seq    SEQUENCE     �   ALTER TABLE public.examtable ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.examtable_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    16473    groups    TABLE     G   CREATE TABLE public.groups (
    name character varying(8) NOT NULL
);
    DROP TABLE public.groups;
       public         heap    postgres    false            �            1259    16476    rooms    TABLE     p   CREATE TABLE public.rooms (
    name character varying(16) NOT NULL,
    type character varying(32) NOT NULL
);
    DROP TABLE public.rooms;
       public         heap    postgres    false            �            1259    16479    teachers    TABLE     �   CREATE TABLE public.teachers (
    id integer NOT NULL,
    course_id integer,
    first_name character varying(64) NOT NULL,
    last_name character varying(64) NOT NULL
);
    DROP TABLE public.teachers;
       public         heap    postgres    false            �            1259    16482    teachers_id_seq    SEQUENCE     �   ALTER TABLE public.teachers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.teachers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    213            �            1259    16483 	   timetable    TABLE     z  CREATE TABLE public.timetable (
    room character varying(8) NOT NULL,
    start_hour integer NOT NULL,
    end_hour integer NOT NULL,
    day character(16) NOT NULL,
    course character varying(64) NOT NULL,
    course_type character varying(32) NOT NULL,
    group_name character varying(16) NOT NULL,
    teacher character varying(128) NOT NULL,
    id integer NOT NULL
);
    DROP TABLE public.timetable;
       public         heap    postgres    false            �            1259    16486    timetable_id_seq    SEQUENCE     �   ALTER TABLE public.timetable ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.timetable_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            t           2606    16488    discipline discipline_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.discipline
    ADD CONSTRAINT discipline_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.discipline DROP CONSTRAINT discipline_pkey;
       public            postgres    false    209            z           2606    16497    examtable examtable_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.examtable
    ADD CONSTRAINT examtable_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.examtable DROP CONSTRAINT examtable_pkey;
       public            postgres    false    217            v           2606    16490    teachers teachers_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.teachers DROP CONSTRAINT teachers_pkey;
       public            postgres    false    213            x           2606    16492    timetable timetable_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.timetable
    ADD CONSTRAINT timetable_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.timetable DROP CONSTRAINT timetable_pkey;
       public            postgres    false    215           