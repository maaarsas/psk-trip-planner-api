INSERT INTO users (id, password, username) VALUES
	(1, '', 'a'),
	(2, '', 'b'),
	(3, '', 'c'),
	(4, '', 'd')
;

INSERT INTO trips (id, start_date, end_date, organizer_id) VALUES
	(1, '2019-04-22', '2019-04-26', 1),
	(2, '2019-04-19', '2019-05-27', 1),
	(3, '2019-02-19', '2019-10-25', 2),
	(4, '2019-12-01', '2019-12-02', 1)
;

INSERT INTO trip_participations (id, status, participant_id, trip_id) VALUES
	(1, 'ACCEPTED', 2, 1),
	(2, 'INVITED', 3, 1),
	(3, 'REJECTED', 3, 1),
	(4, 'ACCEPTED', 1, 3),
	(5, 'ACCEPTED', 2, 3),
	(6, 'INVITED', 2, 4)
;
