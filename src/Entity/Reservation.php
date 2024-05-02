<?php

namespace App\Entity;

use App\Repository\ReservationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;



#[ORM\Entity(repositoryClass: ReservationRepository::class)]
class Reservation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    #[Assert\NotBlank]

    private ?int $Eid = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Positive()]

    private ?string $date_reservation = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(min: 5   )]
    private ?string $statut = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Regex(
        pattern:"/^[a-zA-ZÀ-ÿ '-]+$/u"
    )]

    private ?string $Nom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Regex(
        pattern:"/^[a-zA-ZÀ-ÿ '-]+$/u"
    )]

    private ?string $Prenom = null;

    #[ORM\Column]
    #[Assert\Positive()]
    private ?int $NbPersonne = null;

    #[ORM\Column(length: 255)]
    #[Assert\Email()]
    private ?string $Email = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getEid(): ?int
    {
        return $this->Eid;
    }

    public function setEid(int $Eid): static
    {
        $this->Eid = $Eid;

        return $this;
    }

    public function getDateReservation(): ?string
    {
        return $this->date_reservation;
    }

    public function setDateReservation(string $date_reservation): static
    {
        $this->date_reservation = $date_reservation;

        return $this;
    }

    public function getStatut(): ?string
    {
        return $this->statut;
    }

    public function setStatut(string $statut): static
    {
        $this->statut = $statut;

        return $this;
    }

    public function getNom(): ?string
    {
        return $this->Nom;
    }

    public function setNom(string $Nom): static
    {
        $this->Nom = $Nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->Prenom;
    }

    public function setPrenom(string $Prenom): static
    {
        $this->Prenom = $Prenom;

        return $this;
    }

    public function getNbPersonne(): ?int
    {
        return $this->NbPersonne;
    }

    public function setNbPersonne(int $NbPersonne): static
    {
        $this->NbPersonne = $NbPersonne;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->Email;
    }

    public function setEmail(string $Email): static
    {
        $this->Email = $Email;

        return $this;
    }
}
