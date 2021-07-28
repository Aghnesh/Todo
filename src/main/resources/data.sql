DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(25) NOT NULL,
  status VARCHAR(15) NOT NULL,
  priority VARCHAR(10) NOT NULL,
  created datetime,
  updated datetime
);