<?php

namespace App\Entity;
use App\Repository\CoursRepository;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
#[ORM\Entity(repositoryClass: CoursRepository::class)]
class Cours
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idCours;
    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"the titreCours cannot be blank")]
    private ?string $titreCours = null;
   

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"the dureecours cannot be blank")]
    private ?string $dureeCours = null;
    

    #[ORM\ManyToOne(inversedBy: 'cours')]
    #[ORM\JoinColumn(name: "id", referencedColumnName: "id")]
    private ?Formation $formation = null; 
    

    public function getIdCours(): ?int
    {
        return $this->idCours;
    }

    public function setIdCours(?int $idCours): self
{
    $this->idCours = $idCours;

    return $this;
}


    public function getTitreCours(): ?string
    {
        return $this->titreCours;
    }

    public function setTitreCours(string $titreCours): static
    {
        $this->titreCours = $titreCours;

        return $this;
    }

    public function getDureeCours(): ?string
    {
        return $this->dureeCours;
    }

    public function setDureeCours(string $dureeCours): static
    {
        $this->dureeCours = $dureeCours;

        return $this;
    }



    public function getFormation(): ?Formation
    {
        return $this->formation;
    }

    public function setFormation(?Formation $formation): static
    {
        $this->formation = $formation;

        return $this;
    }


}
