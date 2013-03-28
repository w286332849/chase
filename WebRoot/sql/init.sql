

insert into sys_user(id,username,password,status) values (1,'admin','admin',1);
insert into sys_user(id,username,password,status) values (2,'chase','chase',1);

insert into sys_menu(id,pid,name,url,status) values (1,null,'系统管理',null,1);
insert into sys_menu(id,pid,name,url,status) values (2,1,'用户管理','/framework/user/userList.jsp',1);
insert into sys_menu(id,pid,name,url,status) values (3,1,'菜单管理','/framework/menu/menuList.jsp',1);