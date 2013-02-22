CREATE TABLE DB (
  Id INTEGER NOT NULL AUTO_INCREMENT,
  Name VARCHAR(50) NOT NULL UNIQUE,
  SeqDBIdentifier VARCHAR(50),
  SwissprotEntryId TEXT,
  PRIMARY KEY(Id)
);
CREATE TABLE Organism (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    PRIMARY KEY(Id)
);
CREATE TABLE Keywords (
  Id INT NOT NULL AUTO_INCREMENT,
  Value TEXT NOT NULL,
  PRIMARY KEY(ID)
);
CREATE TABLE Seq (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    Definition TEXT,
    Seq LONGTEXT,
    OrganismId INT,
    Type ENUM('RNA', 'DNA', 'Protein'),
    Structure LONGTEXT,
    DBId Int,
    PRIMARY KEY(Id),
    FOREIGN KEY(OrganismId) references Organism(Id),
    FOREIGN KEY(DBId) references DB(Id)
);
CREATE TABLE StructAlign (
    Id INT NOT NULL AUTO_INCREMENT,
    Seq INT,
    PRIMARY KEY(Id, Seq),
    FOREIGN KEY(Seq) references Seq(Id)
);
CREATE TABLE KeySeq (
    KeywordsId INT,
    SeqId INT,
    PRIMARY KEY(KeywordsId, SeqId),
    FOREIGN KEY(KeywordsId) references Keywords(Id),
    FOREIGN KEY(SeqId) references Seq(Id)
);
