INSERT INTO sokobandatabase.spel(spelNaam) VALUES ('easy');
INSERT INTO sokobandatabase.spel(spelNaam) VALUES ('medium');
INSERT INTO sokobandatabase.spel(spelNaam) VALUES ('hard'); 

/* spelbordnamen voor spellen */
INSERT INTO sokobandatabase.spelbord (spelbordNaam, Spel_spelID) VALUES
('1-1', 1), ('1-2', 1)/*, ('1-3', 1),
('2-1', 2), ('2-2', 2), ('2-3', 2),
('3-1', 3), ('3-2', 3), ('3-3', 3)*/;

/* SPEL: makkelijk */
/* SPELBORD 1-1 */
-- INSERT INTO sokobandatabase.muur (positieX, positieY, Spelbord_spelbordID) VALUES
-- (0,0,1),(0,1,1),(0,2,1),(0,3,1),(0,4,1),(0,5,1),(0,6,1),(0,7,1),(0,8,1),(0,9,1),
-- (1,0,1),(1,1,1),(1,2,1),(1,4,1),(1,5,1),(1,6,1),(1,7,1),(1,8,1),(1,9,1),
-- (2,0,1),(2,1,1),(2,2,1),(2,4,1),(2,5,1),(2,6,1),(2,7,1),(2,8,1),(2,9,1),
-- (3,0,1),(3,1,1),(3,2,1),(3,7,1),(3,8,1),(3,9,1),
-- (4,0,1),(4,5,1),(4,6,1),(4,7,1),(4,8,1),(4,9,1),
-- (5,0,1),(5,1,1),(5,2,1),(5,3,1),(5,5,1),(5,6,1),(5,7,1),(5,8,1),(5,9,1),
-- (6,0,1),(6,1,1),(6,2,1),(6,3,1),(6,5,1),(6,6,1),(6,7,1),(6,8,1),(6,9,1),
-- (7,0,1),(7,1,1),(7,2,1),(7,3,1),(7,4,1),(7,5,1),(7,6,1),(7,7,1),(7,8,1),(7,9,1),
-- (8,0,1),(8,1,1),(8,2,1),(8,3,1),(8,4,1),(8,5,1),(8,6,1),(8,7,1),(8,8,1),(8,9,1),
-- (9,0,1),(9,1,1),(9,2,1),(9,3,1),(9,4,1),(9,5,1),(9,6,1),(9,7,1),(9,8,1),(9,9,1);

INSERT INTO sokobandatabase.veld (positieX, positieY, Spelbord_spelbordID) VALUES
(2,3,1),(3,4,1),(4,2,1);

INSERT INTO sokobandatabase.doel (positieX, positieY, Spelbord_spelbordID) VALUES
(1,3,1),(3,6,1),(4,1,1),(6,4,1);

INSERT INTO sokobandatabase.kist (positieX, positieY, Spelbord_spelbordID) values
(3,3,1),(3,5,1),(4,3,1),(5,4,1);

INSERT INTO sokobandatabase.mannetje (positieX, positieY, Spelbord_spelbordID) VALUES
(4,4,1);
/* EINDE SPELBORD 1-1 */

/* SPELBORD 1-2 */
-- INSERT INTO sokobandatabase.muur (positieX, positieY, Spelbord_spelbordID) VALUES
-- (0,0,2),(0,1,2),(0,2,2),(0,3,2),(0,4,2),(0,5,2),(0,6,2),(0,7,2),(0,8,2),(0,9,2),
-- (1,0,2),(1,1,2),(1,2,2),(1,4,2),(1,5,2),(1,6,2),(1,7,2),(1,8,2),(1,9,2),
-- (2,0,2),(2,1,2),(2,2,2),(2,4,2),(2,5,2),(2,6,2),(2,7,2),(2,8,2),(2,9,2),
-- (3,0,2),(3,1,2),(3,2,2),(3,7,2),(3,8,2),(3,9,2),
-- (4,0,2),(4,5,2),(4,6,2),(4,7,2),(4,8,2),(4,9,2),
-- (5,0,2),(5,1,2),(5,2,2),(5,3,2),(5,5,2),(5,6,2),(5,7,2),(5,8,2),(5,9,2),
-- (6,0,2),(6,1,2),(6,2,2),(6,3,2),(6,5,2),(6,6,2),(6,7,2),(6,8,2),(6,9,2),
-- (7,0,2),(7,1,2),(7,2,2),(7,3,2),(7,4,2),(7,5,2),(7,6,2),(7,7,2),(7,8,2),(7,9,2),
-- (8,0,2),(8,1,2),(8,2,2),(8,3,2),(8,4,2),(8,5,2),(8,6,2),(8,7,2),(8,8,2),(8,9,2),
-- (9,0,2),(9,1,2),(9,2,2),(9,3,2),(9,4,2),(9,5,2),(9,6,2),(9,7,2),(9,8,2),(9,9,2);

INSERT INTO sokobandatabase.veld (positieX, positieY, Spelbord_spelbordID) VALUES
(2,3,2),(3,4,2),(4,2,2);

INSERT INTO sokobandatabase.doel (positieX, positieY, Spelbord_spelbordID) VALUES
(1,3,2),(3,6,2),(4,1,2),(6,4,2);

INSERT INTO sokobandatabase.kist (positieX, positieY, Spelbord_spelbordID) values
(3,3,2),(3,5,2),(4,3,2),(5,4,2);

INSERT INTO sokobandatabase.mannetje (positieX, positieY, Spelbord_spelbordID) VALUES
(4,4,2);
/* EINDE SPELBORD 1-2 */

/* EINDE SPEL: makkelijk */