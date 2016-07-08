use amsio_assignment_mysql;

LOAD DATA LOCAL INFILE '/Users/gregashby/Documents/workspace/java/aimsio-assignment/database/query_result.csv' INTO TABLE SIGNALS
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES;
  
 