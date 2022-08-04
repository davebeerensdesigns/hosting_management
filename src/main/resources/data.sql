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

INSERT INTO client_meta (company_name, company_address, company_city, company_state, company_zipcode, company_country, company_email, company_phone, client_id)
VALUES
('Wok Ninove', 'Brusselsesteenweg 211-213', 'Meerbeke', 'Oost-Vlaanderen', '9402', 'Belgie', 'wokninove@hotmail.com', ' 054 33 28 88', 1);

INSERT INTO client_website (server_address, client_package, client_domain, ssl_type, ssl_status, ssl_expires, client_id)
VALUES
('ns1.wappstarsdns10.nl', 'www.wokninove.be', 'www.wokninove.be', 'PositiveSSL', true, '2023-01-01', 1),
('ns1.wappstarsdns10.nl', 'www.wokninove.be', 'bestellen.wokninove.be', 'PositiveSSL', true, '2023-01-01', 1);