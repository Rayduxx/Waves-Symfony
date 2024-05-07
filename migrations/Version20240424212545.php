<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240424212545 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67D6CE67B80');
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67DFE6E88D7');
        $this->addSql('DROP INDEX fk_6eeaa67dfe6e88d7 ON commande');
        $this->addSql('CREATE INDEX IDX_6EEAA67DFE6E88D7 ON commande (idUser)');
        $this->addSql('DROP INDEX fk_6eeaa67d6ce67b80 ON commande');
        $this->addSql('CREATE INDEX IDX_6EEAA67D6CE67B80 ON commande (idItem)');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67D6CE67B80 FOREIGN KEY (idItem) REFERENCES item (itemID)');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67DFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67DFE6E88D7');
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67D6CE67B80');
        $this->addSql('DROP INDEX idx_6eeaa67dfe6e88d7 ON commande');
        $this->addSql('CREATE INDEX FK_6EEAA67DFE6E88D7 ON commande (idUser)');
        $this->addSql('DROP INDEX idx_6eeaa67d6ce67b80 ON commande');
        $this->addSql('CREATE INDEX FK_6EEAA67D6CE67B80 ON commande (idItem)');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67DFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (id)');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67D6CE67B80 FOREIGN KEY (idItem) REFERENCES item (itemID)');
    }
}
