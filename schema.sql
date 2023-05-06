PGDMP         	                {           timetablecss    15.1    15.0     
           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    28081    timetablecss    DATABASE     �   CREATE DATABASE timetablecss WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1252';
    DROP DATABASE timetablecss;
                postgres    false            �            1259    28082 
   discipline    TABLE     �   CREATE TABLE public.discipline (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    year integer NOT NULL
);
    DROP TABLE public.discipline;
       public         heap    postgres    false            �            1259    28111    discipline_id_seq    SEQUENCE     �   ALTER TABLE public.discipline ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.discipline_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �            1259    28089    groups    TABLE     G   CREATE TABLE public.groups (
    name character varying(8) NOT NULL
);
    DROP TABLE public.groups;
       public         heap    postgres    false            �            1259    28106    rooms    TABLE     p   CREATE TABLE public.rooms (
    name character varying(16) NOT NULL,
    type character varying(32) NOT NULL
);
    DROP TABLE public.rooms;
       public         heap    postgres    false            �            1259    28099    teachers    TABLE     �   CREATE TABLE public.teachers (
    id integer NOT NULL,
    course_id integer,
    first_name character varying(64) NOT NULL,
    last_name character varying(64) NOT NULL
);
    DROP TABLE public.teachers;
       public         heap    postgres    false            �            1259    28112    teachers_id_seq    SEQUENCE     �   ALTER TABLE public.teachers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.teachers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    28094 	   timetable    TABLE     X  CREATE TABLE public.timetable (
    room character varying(8) NOT NULL,
    start_hour integer NOT NULL,
    end_hour integer NOT NULL,
    day character(16) NOT NULL,
    course character varying(64) NOT NULL,
    course_type character varying(32) NOT NULL,
    group_name character varying(16) NOT NULL,
    teacher character varying(128)
);
    DROP TABLE public.timetable;
       public         heap    postgres    false            w           2606    28088    discipline discipline_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.discipline
    ADD CONSTRAINT discipline_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.discipline DROP CONSTRAINT discipline_pkey;
       public            postgres    false    214            y           2606    28105    teachers teachers_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.teachers DROP CONSTRAINT teachers_pkey;
       public            postgres    false    217           