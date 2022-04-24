insert into roles (description) values ('admin'), ('member');
insert into users (username, password, role_id) values ('admin', '$2a$12$2IA/5wXUSsYaCIRKDR2Xie8oUfBHjbXHHUE1jz5fR6/PfIN66QcEG', 1);
insert into users (username, password, role_id) values ('member', '$2a$12$h.7.bzkOHGJm4MjusQJgj.nULlGZxgeaAeKbqk/Ulnjgv3.yDkYW6', 2);