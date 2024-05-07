<?php

namespace App\Controller;
use App\Entity\Formation;

use App\Repository\FormationRepository;
use App\Repository\CoursRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use App\Service\FormationService;

class StatsController extends AbstractController
{
    #[Route('/stats', name: 'app_formation_stat')]
    public function stats(CoursRepository $coursRepository,FormationService $formationService)
    {
        $formationNotif =$formationService->Notif();
    $stats = $coursRepository->getStatsByType();

    return $this->render('cours/stats.html.twig', [
        'stats' => $stats,
        'formationNotif' => $formationNotif
    ]);
    }
}