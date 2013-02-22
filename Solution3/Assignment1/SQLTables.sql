CREATE TABLE SeqDB (
    Id INTEGER NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY(Id)
);
CREATE TABLE Keywords (
  Id INT NOT NULL AUTO_INCREMENT,
  Description TEXT NOT NULL,
  PRIMARY KEY(ID)
);
CREATE TABLE SecondStruct(
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
  Name ENUM('mRNA', 'tRNA', 'RNA', 'DNA', 'Peptid'),
  IntronExon ENUM('Intron', 'Exon', 'not defined'),
  Description TEXT,
  PRIMARY KEY(Id)
);
CREATE TABLE SeqDBEntry (
    Id INT NOT NULL AUTO_INCREMENT,
    SeqDB INT,
    SeqDBIdentifier VARCHAR(50),
    PRIMARY KEY(Id),
    FOREIGN KEY(SeqDB) references SeqDB(Id)
);
CREATE TABLE Seq (
    Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    Definition TEXT,
    Seq LONGTEXT,
    Organism INT,
    SeqTyp INT,
    Typ INT,
    secStruct INT,
    PRIMARY KEY(Id),
    FOREIGN KEY(Organism) references Organism(Id),
    FOREIGN KEY(Typ) references Type(Id),
    FOREIGN KEY(secStruct) references SecondStruct(Id)
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
    Seq1 INT,
    Seq2 INT,
    PRIMARY KEY(Id),
    FOREIGN KEY(Seq1) references Seq(Id),
    FOREIGN KEY(Seq2) references Seq(Id)
);

