CREATE TABLE DB (
  Id INTEGER NOT NULL AUTO_INCREMENT,
  Name VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY(Id)
);
CREATE TABLE Organism (
  Id INT NOT NULL AUTO_INCREMENT,
  Name TEXT NOT NULL,
  PRIMARY KEY(Id)
);
CREATE TABLE Keywords (
  Id INT NOT NULL AUTO_INCREMENT,
  Value TEXT NOT NULL,
  PRIMARY KEY(ID)
);
CREATE TABLE Seq (
  Id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(50) NOT NULL UNIQUE,
  Definition TEXT,
  Seq LONGTEXT NOT NULL,
  OrganismId INT,
  Type ENUM('RNA', 'DNA', 'Protein', 'Genome'),
  Structure LONGTEXT,
  PRIMARY KEY(Id),
  FOREIGN KEY(OrganismId) references Organism(Id)
);
CREATE TABLE Orf (
  Id INT NOT NULL AUTO_INCREMENT,
  SeqId INT,
  Start INT,
  Stop INT,
  PRIMARY KEY(Id),
  FOREIGN KEY(SeqId) references Seq(Id)
);
CREATE TABLE StructAlign (
  Id INT NOT NULL AUTO_INCREMENT,
  SeqId INT,
  PRIMARY KEY(Id, SeqId),
  FOREIGN KEY(SeqId) references Seq(Id)
);
CREATE TABLE KeySeq (
  KeywordsId INT,
  SeqId INT,
  PRIMARY KEY(KeywordsId, SeqId),
  FOREIGN KEY(KeywordsId) references Keywords(Id),
  FOREIGN KEY(SeqId) references Seq(Id)
);
CREATE TABLE Source (
  DBId INT,
  SeqId INT,
  SeqDBIdentifier VARCHAR(50),
  SwissprotEntryId TEXT,
  PRIMARY KEY(DBId, SeqId),
  FOREIGN KEY(DBId) references DB(Id),
  FOREIGN KEY(SeqId) references Seq(Id)
);
