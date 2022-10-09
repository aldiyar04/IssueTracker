INSERT INTO users (role, email, username, password) VALUES
    ('Admin', 'admin@test.com', 'Admin', '$2a$10$KhJM993ZyhJxRZfztJtUzeUbPGK5BV3nCl0ZqWe5AM59uKUV1bVBC'),
    ('Manager', 'manager1@test.com', 'Manager1', '$2a$10$UydTYhiLdYLFuhvXf0N1c.k.ZdU377P73BShFBZ5kJOIlEyjN89aW'),
    ('Manager', 'manager2@test.com', 'Manager2', '$2a$10$syRFOkGJ.FE6p3Ixb8PU0OnfInv5HLPPWToG95hfSgPBBRpbSd09m'),
    ('Lead Dev', 'allaissen@gmail.com', 'LeadDev1', '$2a$10$htB.IBFHV2dC7pBh1mbdDeDZD4503OBl5RUNasXfjVJmipUjS7PwS'),
    ('Lead Dev', 'leaddev2@test.com', 'LeadDev2', '$2a$10$QW/.id/RT7CyT8XDf7zVJO7Ixyqw95GnRf6twGV3qyhdTV5CgntoC'),
    ('Lead Dev', 'leaddev3@test.com', 'LeadDev3', '$2a$10$qcxagl/i3vF4QUP5XvuKyOp4o2ZIsuP29eCNAVYhffsPXeLlBOBy2'),
    ('Developer', 'developer1@test.com', 'Developer1', '$2a$10$b/NltOjVgtBiIvjkB3lxUu/fuCN7K4Ofww.6f14e3E8103E6G9nRO'),
    ('Developer', 'developer2@test.com', 'Developer2', '$2a$10$66e5kgsU4evTqWpJFPENm.pUmDf2BmayBPCZjscA3rQ6825DJ/L5u'),
    ('Developer', 'developer3@test.com', 'Developer3', '$2a$10$9mT1oTz7c7NMuu3J0fcgI.ltuFV5jHAndcVKE1A3SX3PQ2lTGvB7C'),
    ('Developer', 'developer4@test.com', 'Developer4', '$2a$10$b5KQGgDINNr7L6.v0N3NBeGMpGGC.eJP3u54Hk7g78oATwMlxEeym');

INSERT INTO projects (name, description, lead_dev_id) VALUES
    ('Text Editor',
        'Notepad style application that can open, edit, and save text documents.',
        null),
    ('RSS Feed Creator',
        'A program which can read in text from other sources and put it in RSS or Atom news format for syndication.',
        5),
    ('Text to HTML Generator',
        'Converts text files into web HTML files and stylizes them. Great for making online documentation of standard text documentation',
        6);

INSERT INTO project_assignees (project_id, assignee_id) VALUES
    (1, 8),
    (1, 9),
    (2, 10);


INSERT INTO tickets (title, project_id, submitter_id, type, status, priority, target_res_date) VALUES
    ('Issue 1', 1, 2, 'Bug', 'New', 'High', '2022-03-12'),
    ('Issue 2', 1, 2, 'Refactoring', 'New', 'Medium', '2022-03-15'),
    ('Issue 3', 2, 2, 'Vulnerability', 'New', 'Critical', '2022-03-11'),
    ('Issue 4', 2, 3, 'Feature Request', 'New', 'High', '2022-03-30'),
    ('Issue 5', 3, 3, 'Bug', 'New', 'High', '2022-03-13'),
    ('Issue 6', 3, 3, 'Bug', 'New', 'Medium', '2022-03-20');
