<?php

namespace App\Entity;

use GuzzleHttp\Psr7\Message;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\EventRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: EventRepository::class)]
class Event
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(max: 255 )]
    #[Assert\Regex(
        pattern:"/^[a-zA-ZÀ-ÿ '-]+$/u"
    )]
    private ?string $nomE = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(max: 255)]
    private ?string $adrE = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(min: 5   )]
    private ?string $descr = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
  
    private ?string $date = null;

    #[ORM\Column(type: 'string', length: 255, nullable: true)]
    private  $image;
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomE(): ?string
    {
        return $this->nomE;
    }

    public function setNomE(string $nomE): static
    {
        $this->nomE = $nomE;

        return $this;
    }

    public function getAdrE(): ?string
    {
        return $this->adrE;
    }

    public function setAdrE(string $adrE): static
    {
        $this->adrE = $adrE;

        return $this;
    }

    public function getDescr(): ?string
    {
        return $this->descr;
    }

    public function setDescr(string $descr): static
    {
        $this->descr = $descr;

        return $this;
    }

    public function getDate(): ?string
    {
        return $this->date;
    }

    public function setDate(string $date): static
    {
        $this->date = $date;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }
    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }
}
