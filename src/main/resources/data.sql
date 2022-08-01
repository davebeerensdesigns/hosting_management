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

INSERT INTO client (date_created, date_modified, client_id, client_name)
VALUES
(localtimestamp, localtimestamp, 1, 'Client 1');

INSERT INTO client_meta (meta_key, meta_value, client_id)
VALUES
('_client_meta_phone', '0612345678', 1),
('_client_meta_email', 'user@wappfood.nl', 1),
('_client_meta_company', 'Wappstars', 1),
('_client_meta_address', 'hurksestraat 28b', 1),
('_client_meta_city', 'Eindhoven', 1),
('_client_meta_state', 'Noord Brabant', 1),
('_client_meta_postcode', '1234 aa', 1),
('_client_meta_country', 'Nederland', 1);