INSERT INTO USER(userId, name, login, password, amount, managerId, role, createdDate, updatedDate) VALUES (0, 'FIO', 'admin', 'admin', 0, 0, 'ROLE_ADMIN', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO USER(userId, name, login, password, amount, managerId, role, createdDate, updatedDate) VALUES (1, 'FIO1', 'login1', 'password', 0, 1, 'ROLE_MANAGER', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO USER(userId, name, login, password, amount, managerId, role, createdDate, updatedDate) VALUES (2, 'FIO2', 'login2', 'password', 0, 2, 'ROLE_SUBORDINATE', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.000', 'DD/MM/YYYY HH:MI:SS'));

INSERT INTO REQUEST(requestId, userId, status, description, createdDate, updatedDate) VALUES (1, 1, 'OPEN', 'Request 01', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO REQUEST(requestId, userId, status, description, createdDate, updatedDate) VALUES (2, 1, 'IN_PROGRESS', 'Request 02', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO REQUEST(requestId, userId, status, description, createdDate, updatedDate) VALUES (3, 1, 'CANCEL', 'Request 03', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO REQUEST(requestId, userId, status, description, createdDate, updatedDate) VALUES (4, 1, 'CLOSED', 'Request 04', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO REQUEST(requestId, userId, status, description, createdDate, updatedDate) VALUES (5, 2, 'OPEN', 'Request 05', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));
INSERT INTO REQUEST(requestId, userId, status, description, createdDate, updatedDate) VALUES (6, 2, 'IN_PROGRESS', 'Request 06', TO_TIMESTAMP('14/10/2015 20:30:00', 'DD/MM/YYYY HH:MI:SS'), TO_TIMESTAMP('14/10/2015 20:30:00.099', 'DD/MM/YYYY HH:MI:SS'));

INSERT INTO ACTION (actionId, requestId, type, points) VALUES (1, 1, 'IMPORT_REPORT', 2);
INSERT INTO ACTION (actionId, requestId, type, points) VALUES (2, 1, 'CANCEL_REQ', -2);
INSERT INTO ACTION (actionId, requestId, type, points) VALUES (3, 1, 'IMPORT_IN_PROGRESS_REQ', 0.5);
INSERT INTO ACTION (actionId, requestId, type, points) VALUES (4, 1, 'DELETE_IN_PROGRESS_REQ', -0.5);
INSERT INTO ACTION (actionId, requestId, type, points) VALUES (5, 1, 'IMPORT_CLOSED_REQ', 1);
INSERT INTO ACTION (actionId, requestId, type, points) VALUES (6, 1, 'DELETE_CLOSED_REQ', -1);
