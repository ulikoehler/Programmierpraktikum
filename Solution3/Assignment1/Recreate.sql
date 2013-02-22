DROP TABLE KeySeq;
DROP TABLE StructAlign;
DROP TABLE Source;
DROP TABLE Seq;
DROP TABLE Keywords;
DROP TABLE SecondStruct;
DROP TABLE Organism;
DROP TABLE Type;
DROP TABLE SeqDBEntry;
DROP TABLE SeqDB;
CREATE TABLE SeqDB (
    Id INTEGER NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY(Id)
);
CREATE TABLE Keywords (
  Id INT NOT NULL AUTO_INCREMENT,
  Value TEXT NOT NULL,
  PRIMARY KEY(ID)
);
CREATE TABLE SecondStruct (
    Id INT NOT NULL AUTO_INCREMENT,
    Struct TEXT NOT NULL,
    PRIMARY KEY(Id)
);
CREATE TABLE Organism (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(255),
    PRIMARY KEY(Id)
);
CREATE TABLE Type (
  Id INT NOT NULL AUTO_INCREMENT,
  Name VARCHAR(50),
  Description TEXT,
  PRIMARY KEY(Id)
);
CREATE TABLE SeqDBEntry (
    Id INT NOT NULL AUTO_INCREMENT,
    SeqDBId INT,
    SeqDBIdentifier VARCHAR(50),
    SwissprotEntryId TEXT,
    PRIMARY KEY(Id),
    FOREIGN KEY(SeqDBId) references SeqDB(Id)
);
CREATE TABLE Seq (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    Definition TEXT,
    Seq LONGTEXT,
    OrganismId INT,
    TypeId INT,
    secStructId INT,
    PRIMARY KEY(Id),
    FOREIGN KEY(OrganismId) references Organism(Id),
    FOREIGN KEY(TypeId) references Type(Id),
    FOREIGN KEY(secStructId) references SecondStruct(Id)
);
CREATE TABLE Source (
    SeqId INT,
    EntryId Int,
    PRIMARY KEY(SeqId, EntryId),
    FOREIGN KEY(SeqId) references Seq(Id),
    FOREIGN KEY(EntryId) references SeqDBEntry(Id)
);
CREATE TABLE StructAlign (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    Seq1Id INT,
    Seq2Id INT,
    PRIMARY KEY(Id),
    FOREIGN KEY(Seq1Id) references Seq(Id),
    FOREIGN KEY(Seq2Id) references Seq(Id)
);
CREATE TABLE KeySeq (
    KeywordsId INT,
    SeqId INT,
    PRIMARY KEY(KeywordsId, SeqId),
    FOREIGN KEY(KeywordsId) references Keywords(Id),
    FOREIGN KEY(SeqId) references Seq(Id)
);
INSERT INTO SeqDB (`Name`) VALUES('SwissProt');    
INSERT INTO Type (`Name`) VALUES('DNA');
INSERT INTO Type (`Name`) VALUES('RNA');
INSERT INTO Type (`Name`) VALUES('Protein');
