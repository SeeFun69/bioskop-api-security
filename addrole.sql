CREATE EXTENSION pgcrypto;

INSERT INTO public."role"
(id, "name")
VALUES(1, 'ROLE_USER');

INSERT INTO public."role"
(id, "name")
VALUES(2, 'ROLE_ADMIN');



INSERT INTO public.users_roles
(user_user_id, roles_id)
values
(1, 2),
(2, 1),
(3, 2),
(4, 1),
(5, 2),
(6, 1),
(7, 2),
(8, 1),
(9, 1),
(10, 2);

UPDATE public.Users SET password = crypt("password" ,gen_salt('bf'));