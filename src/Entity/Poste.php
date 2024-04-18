<?php

namespace App\Entity;

use App\Repository\PosteRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;


#[ORM\Entity(repositoryClass: PosteRepository::class)]

class Poste
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]

    private ?int $idposte = null;
    
    #[Assert\NotBlank(message:"the name cannot be blank")]
    #[ORM\Column]
    #[Assert\Length(min: 3, max: 255, minMessage: "the title must be at least {{ limit }} characters long")]

    private ?String $titre = null;
    

    #[ORM\Column] 
    #[Assert\NotBlank(message:"the name cannot be blank")]
    #[Assert\Length(min: 3, max: 255, minMessage: "the actor must be at least {{ limit }} characters long")]
    private ?String $artiste = null;

   

    #[ORM\Column]
    private ?String $image = null;

    #[ORM\Column]  
    private ?String $morceau = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"the name cannot be blank")]

    private ?String $description = null;

    public function getIdposte(): ?int
    {
        return $this->idposte;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): static
    {
        $this->titre = $titre;

        return $this;
    }

    public function getArtiste(): ?string
    {
        return $this->artiste;
    }

    public function setArtiste(string $artiste): static
    {
        $this->artiste = $artiste;

        return $this;
    }

  

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): static
    {
        $this->image = $image;

        return $this;
    }

    public function getMorceau(): ?string
    {
        return $this->morceau;
    }

    public function setMorceau(string $morceau): static
    {
        $this->morceau = $morceau;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }


}