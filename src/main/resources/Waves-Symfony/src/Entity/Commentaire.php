<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\CommentaireRepository;
use Symfony\Component\Validator\Constraints as Assert;
#[ORM\Entity(repositoryClass: CommentaireRepository::class)]
class Commentaire
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $idcomm = null;
   

    #[Assert\NotBlank(message:"the name cannot be blank")]
    #[ORM\Column]
    #[Assert\Length(min: 3, max: 255, minMessage: "the comment must be at least {{ limit }} characters long")]
    private ?String $comment = null;

    #[ORM\Column]
    private ?int $iduser = null;
    

  
    #[ORM\ManyToOne(inversedBy: 'commentaires')]
    #[ORM\JoinColumn(name: "idposte", referencedColumnName: "idposte")]
    private ?Poste $idPoste = null;


    public function getIdcomm(): ?int
    {
        return $this->idcomm;
    }

    public function getComment(): ?string
    {
        return $this->comment;
    }

    public function setComment(string $comment): static
    {
        $this->comment = $comment;

        return $this;
    }

    public function getIduser(): ?int
    {
        return $this->iduser;
    }

    public function setIduser(?int $iduser): static
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getIdposte(): ?Poste
    {
        return $this->idPoste;
    }

    public function setIdposte(?Poste $idposte): self
    {
        $this->idPoste = $idposte;

        return $this;
    }


}
