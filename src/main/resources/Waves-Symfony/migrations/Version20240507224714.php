<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240507224714 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE commande (idc INT AUTO_INCREMENT NOT NULL, total DOUBLE PRECISION NOT NULL, dateC DATETIME NOT NULL, idUser INT DEFAULT NULL, idItem INT DEFAULT NULL, INDEX IDX_6EEAA67DFE6E88D7 (idUser), INDEX IDX_6EEAA67D6CE67B80 (idItem), PRIMARY KEY(idc)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE cours (id_cours INT AUTO_INCREMENT NOT NULL, id INT DEFAULT NULL, titre_cours VARCHAR(255) NOT NULL, duree_cours VARCHAR(255) NOT NULL, INDEX IDX_FDCA8C9CBF396750 (id), PRIMARY KEY(id_cours)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE formation (id INT AUTO_INCREMENT NOT NULL, titre VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, affiche VARCHAR(255) NOT NULL, video VARCHAR(255) NOT NULL, created_at DATETIME NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE item (itemID INT AUTO_INCREMENT NOT NULL, titre VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, auteur VARCHAR(255) NOT NULL, prix DOUBLE PRECISION NOT NULL, PRIMARY KEY(itemID)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67DFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (id)');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67D6CE67B80 FOREIGN KEY (idItem) REFERENCES item (itemID)');
        $this->addSql('ALTER TABLE cours ADD CONSTRAINT FK_FDCA8C9CBF396750 FOREIGN KEY (id) REFERENCES formation (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67DFE6E88D7');
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67D6CE67B80');
        $this->addSql('ALTER TABLE cours DROP FOREIGN KEY FK_FDCA8C9CBF396750');
        $this->addSql('DROP TABLE commande');
        $this->addSql('DROP TABLE cours');
        $this->addSql('DROP TABLE formation');
        $this->addSql('DROP TABLE item');
    }
}
