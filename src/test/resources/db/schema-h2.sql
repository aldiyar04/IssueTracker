DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS project_assignees;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    version BIGINT DEFAULT 1 NOT NULL,
    role VARCHAR(10) NOT NULL,
    email VARCHAR(60) UNIQUE NOT NULL,
    username VARCHAR(30) UNIQUE NOT NULL,
    password CHAR(60) NOT NULL,
    created_on DATE DEFAULT CURRENT_DATE NOT NULL,
    CHECK (role IN ('Admin', 'Manager', 'Lead Dev', 'Developer'))
);

CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    version BIGINT DEFAULT 1 NOT NULL,
    name VARCHAR(60) UNIQUE NOT NULL,
    description TEXT NOT NULL,
    lead_dev_id BIGINT REFERENCES users(id),
    created_on DATE DEFAULT CURRENT_DATE NOT NULL,
    updated_on DATE DEFAULT CURRENT_DATE NOT NULL
);

CREATE TABLE project_assignees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    version BIGINT DEFAULT 1 NOT NULL,
    project_id BIGINT NOT NULL REFERENCES projects(id),
    assignee_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    version BIGINT DEFAULT 1 NOT NULL,
    title VARCHAR(40) NOT NULL,
    description TEXT,
    project_id BIGINT NOT NULL REFERENCES projects(id),
    assignee_id BIGINT REFERENCES users(id),
    submitter_id BIGINT REFERENCES users(id),
    type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    priority VARCHAR(30) NOT NULL,
    target_res_date DATE,
    actual_res_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHECK (type IN ('Bug', 'Vulnerability', 'Feature Request', 'Refactoring', 'Other')),
    CHECK(status IN ('New', 'Assigned', 'In Progress', 'Submitted', 'Extra Work Required', 'Resolved')),
    CHECK(priority IN ('Critical', 'High', 'Medium', 'Low', 'None'))
);
