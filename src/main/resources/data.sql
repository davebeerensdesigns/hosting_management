INSERT INTO users (date_created, date_modified, username, apikey, email, enabled, password)
VALUES
(localtimestamp, localtimestamp, 'user', 'fdgtrthahrddfa','user@website.nl', TRUE, '$2a$12$2NGXAW0XlPs2uKQpgtSzHeGsxqnDYmfr/6B1vmpqJWXjiyNRupobO'),
(localtimestamp, localtimestamp, 'admin', 'greahgrtwsreg', 'admin@website.nl', TRUE, '$2a$12$2NGXAW0XlPs2uKQpgtSzHeGsxqnDYmfr/6B1vmpqJWXjiyNRupobO');

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('user', 'ROLE_CUSTOMER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');