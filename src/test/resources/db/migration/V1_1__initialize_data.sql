INSERT INTO t_mt_user (id, username, password, first_name, last_name, role)
VALUES ('6c4364f2-b5f5-482f-8aac-fdadeb38c71d'::uuid, 'test', '$2a$10$dkz2FOrCrK9NfbmjonMnX.W4Wg6LDHbRTMq0Wmf2hKr/K1i9RxR8K', 'Test', 'One', 'ADMIN'),
       ('18e8beb5-a15d-4f95-a478-f01721e7be52'::uuid, 'test2', '$2a$10$OACaswVdi1Z3NjW.2Scyj.yhgtjlw5mp6OCn8atsGVDjb6a/y3tgS', 'Test', 'Two', 'ADMIN');

INSERT INTO t_mt_task (id, user_id, description, status, create_dt, update_dt)
VALUES ('00229171-2f5d-48cb-9870-f622108cea45'::uuid, '6c4364f2-b5f5-482f-8aac-fdadeb38c71d'::uuid, 'Buy Milks', 'UNCHECKED', '2023-01-04 19:00', NULL),
       ('10229171-2f5d-48cb-9870-f622108cea45'::uuid, '6c4364f2-b5f5-482f-8aac-fdadeb38c71d'::uuid, 'Buy Cheeses', 'CHECKED', '2023-01-04 19:01', '2023-01-04 19:02'),
       ('20229171-2f5d-48cb-9870-f622108cea45'::uuid, '6c4364f2-b5f5-482f-8aac-fdadeb38c71d'::uuid, 'Buy Breads', 'CHECKED', '2023-01-04 19:03', '2023-01-04 19:04'),
       ('30229171-2f5d-48cb-9870-f622108cea45'::uuid, '6c4364f2-b5f5-482f-8aac-fdadeb38c71d'::uuid, 'Buy Jams', 'CHECKED', '2023-01-04 19:05', '2023-01-04 19:06'),
       ('40229171-2f5d-48cb-9870-f622108cea45'::uuid, '6c4364f2-b5f5-482f-8aac-fdadeb38c71d'::uuid, 'Buy Chocolates', 'CHECKED', '2023-01-04 19:07', '2023-01-04 19:08'),
       ('a8bcc0f8-f3a9-4686-828a-2b15da002c6e'::uuid, '18e8beb5-a15d-4f95-a478-f01721e7be52'::uuid, 'Buy Noodles', 'UNCHECKED', '2023-01-04 19:10', NULL),
       ('b8bcc0f8-f3a9-4686-828a-2b15da002c6e'::uuid, '18e8beb5-a15d-4f95-a478-f01721e7be52'::uuid, 'Buy Eggs', 'CHECKED', '2023-01-04 19:11', '2023-01-04 19:12'),
       ('c8bcc0f8-f3a9-4686-828a-2b15da002c6e'::uuid, '18e8beb5-a15d-4f95-a478-f01721e7be52'::uuid, 'Buy Chillis', 'CHECKED', '2023-01-04 19:13', '2023-01-04 19:14'),
       ('d8bcc0f8-f3a9-4686-828a-2b15da002c6e'::uuid, '18e8beb5-a15d-4f95-a478-f01721e7be52'::uuid, 'Buy Soy Sauces', 'CHECKED', '2023-01-04 19:15', '2023-01-04 19:16'),
       ('e8bcc0f8-f3a9-4686-828a-2b15da002c6e'::uuid, '18e8beb5-a15d-4f95-a478-f01721e7be52'::uuid, 'Buy Garlics', 'CHECKED', '2023-01-04 19:17', '2023-01-04 19:18');