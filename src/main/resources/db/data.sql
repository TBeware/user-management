INSERT INTO person VALUES (1, 'Admin', 'Admin', '1980-01-01', 'admin@yourcompany.com');

INSERT INTO permission VALUES (1, 'Admin');
INSERT INTO permission VALUES (2, 'Update');
INSERT INTO permission VALUES (3, 'Delete');
INSERT INTO permission VALUES (4, 'Add');

INSERT INTO granted_permissions VALUES (1,1, '1900-01-01');
INSERT INTO granted_permissions VALUES (1,2, '1900-01-02');