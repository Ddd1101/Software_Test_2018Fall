DELETE FROM theatre_ticket.ticket;
DELETE FROM theatre_ticket.member;
DELETE FROM theatre_ticket.show;
INSERT INTO theatre_ticket.show (title, screening, start_time, price, seat_num, state) VALUES ('上海交大迎新晚会', 'A0001', '2018-10-10 18:00:00', '100.00', '10000', '1');
INSERT INTO theatre_ticket.show (title, screening, start_time, price, seat_num, state) VALUES ('念念手记', 'A0001', '2018-10-10 17:20:00', '25.00', '200', '1');
INSERT INTO theatre_ticket.show (title, screening, start_time, price, seat_num, state) VALUES ('念念手记', 'A0002', '2018-10-10 19:20:00', '30.00', '200', '1');
INSERT INTO theatre_ticket.show (title, screening, start_time, price, seat_num, state) VALUES ('念念手记', 'A0003', '2018-10-11 17:20:00', '25.00', '200', '1');
INSERT INTO theatre_ticket.member (member_id, account, consumption) VALUES ('A0001', '1400.00', '5000.00');
INSERT INTO theatre_ticket.member (member_id, account, consumption) VALUES ('A0002', '1400.00', '5000.00');
INSERT INTO theatre_ticket.ticket (ticket_id, title, screening, seat, price, member_id, state) VALUES ('1', '上海交大迎新晚会', 'A0001', '1', '100.00', 'A0001', '1');
INSERT INTO theatre_ticket.ticket (ticket_id, title, screening, seat, price, member_id, state) VALUES ('2', '上海交大迎新晚会', 'A0001', '2', '100.00', 'A0002', '1');
