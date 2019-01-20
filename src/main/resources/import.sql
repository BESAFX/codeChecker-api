INSERT INTO `codechecker`.`privilege` (`id`, `name`,`description`) VALUES ('1', 'ROLE_VIEW_PROJECTS_PRIVILEGE','View Projects');
INSERT INTO `codechecker`.`privilege` (`id`, `name`,`description`) VALUES ('2', 'ROLE_VIEW_ALL_ISSUES_PRIVILEGE','View All Issues');
INSERT INTO `codechecker`.`privilege` (`id`, `name`,`description`) VALUES ('3', 'ROLE_VIEW_RULES_PRIVILEGE','View Rules');
INSERT INTO `codechecker`.`privilege` (`id`, `name`,`description`) VALUES ('4', 'ROLE_VIEW_ADMIN_PRIVILEGE','View Adminstration');
INSERT INTO `codechecker`.`privilege` (`id`, `name`,`description`) VALUES ('5', 'ROLE_VIEW_QUALITY_PRIVILEGE','View Quality Profiles');
INSERT INTO `codechecker`.`privilege` (`id`, `name`,`description`) VALUES ('6', 'ROLE_EDIT_ROLE','Edit Roles');


INSERT INTO `codechecker`.`user_details` (`id`, `email`, `first_name`, `last_name`, `mobile`, `hourly_rate`) VALUES ('1', 're@rmgs.co', 'reh', 'sa', '01008999151', '11.5');
INSERT INTO `codechecker`.`user` (`id`, `active` , `password`, `username`,`user_datils_id`) VALUES ('1', 1,  '$2a$10$I7UP2bxgX7LUb75rPZzX3eN6QoN8fAEJUKH1xRC9CabnrndyByEGu', 'user','1');
INSERT INTO `codechecker`.`role` (`id`, `name`,`description`) VALUES ('1', 'ROLE_SYSTEM_ADMIN','System Admin');
INSERT INTO `codechecker`.`users_roles` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `codechecker`.`roles_privileges` (`role_id`, `privilege_id`) VALUES ('1', '1');
INSERT INTO `codechecker`.`roles_privileges` (`role_id`, `privilege_id`) VALUES ('1', '2');
INSERT INTO `codechecker`.`roles_privileges` (`role_id`, `privilege_id`) VALUES ('1', '3');
INSERT INTO `codechecker`.`roles_privileges` (`role_id`, `privilege_id`) VALUES ('1', '4');
INSERT INTO `codechecker`.`roles_privileges` (`role_id`, `privilege_id`) VALUES ('1', '5');
INSERT INTO `codechecker`.`roles_privileges` (`role_id`, `privilege_id`) VALUES ('1', '6');


