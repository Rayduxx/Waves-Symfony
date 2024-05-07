<?php

namespace App\Service;

use App\Repository\FormationRepository;
use Doctrine\ORM\EntityManagerInterface;

class FormationService
{
    private $entityManager;
    private $formationRepository;

    public function __construct(EntityManagerInterface $entityManager, FormationRepository $formationRepository)
    {
        $this->entityManager = $entityManager;
        $this->formationRepository = $formationRepository;
    }

    public function Notif()
    {
        $today = new \DateTime('today');
        
        $formations = $this->formationRepository->createQueryBuilder('formation')
            ->where('formation.created_at >= :today')
            ->setParameter('today', $today)
            ->orderBy('formation.id', 'DESC')
            ->getQuery()
            ->getResult();

        return $formations;
    }
}
