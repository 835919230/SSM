INSERT INTO sys_roles VALUES (1001,'student','学生只能看学生页面（废话）',1),
                              (1002,'teacher','教师',1),
                              (1003,'manager','系统管理员',1);

INSERT INTO sys_permissions VALUES (1001,'view_student_page','查看学生页面',1),
  (1002,'view_teacher_page','查看教师页面',1),
  (1003,'view_manager_page','查看管理员页面',1);

INSERT INTO sys_roles_permissions VALUES (1001,1001),(1002,1002),(1003,1003);

INSERT INTO sys_users_roles VALUES (1001,1001),(1002,1002),(1003,1003);

SELECT
  *
FROM
  sys_permissions AS sp LEFT JOIN (SELECT sur.user_id,srp.permission_id FROM sys_users_roles AS sur LEFT JOIN sys_roles_permissions AS srp ON sur.role_id = srp.role_id)
    ON sp.id = srp.permissions_id;

SELECT * FROM sys_permissions WHERE permission_id = (SELECT
               srp.permission_id
             FROM sys_users_roles AS sur LEFT JOIN sys_roles_permissions AS srp
                 ON sur.role_id = srp.role_id
             WHERE sur.user_id = 1001);