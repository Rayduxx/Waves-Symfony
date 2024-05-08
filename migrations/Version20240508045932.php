<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240508045932 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE commentaire (idcomm INT AUTO_INCREMENT NOT NULL, idposte INT DEFAULT NULL, comment VARCHAR(255) NOT NULL, iduser INT NOT NULL, INDEX IDX_67F068BCAA8C63B2 (idposte), PRIMARY KEY(idcomm)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE poste (idposte INT AUTO_INCREMENT NOT NULL, titre VARCHAR(255) NOT NULL, artiste VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, morceau VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, PRIMARY KEY(idposte)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT FK_67F068BCAA8C63B2 FOREIGN KEY (idposte) REFERENCES poste (idposte)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commentaire DROP FOREIGN KEY FK_67F068BCAA8C63B2');
        $this->addSql('DROP TABLE commentaire');
        $this->addSql('DROP TABLE poste');
    }
}
