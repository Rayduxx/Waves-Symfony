<?php

namespace App\Controller;

use App\Entity\Cours;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CoursRepository;
use App\Service\FormationService;
use Knp\Component\Pager\PaginatorInterface;
use App\Repository\CommentaireRepository;
use App\Entity\Commentaire;




class TriController extends AbstractController
{
    #[Route('/tri', name: 'app_tri')]
    

    public function displaySortedByNomASC(CoursRepository $coursRepository, FormationService $formationService)
    {
        $formationNotif = $formationService->notif(); // Respectez la casse correcte pour les méthodes
        $cours = $coursRepository->findBy([], ['titreCours' => 'ASC']); // Utilisation du repository injecté

        return $this->render('cours/index.html.twig', [
            'cours' => $cours,
            'formationNotif' => $formationNotif,
        ]);
    }

    

    public function displaySortedByNomDESC(CoursRepository $coursRepository, FormationService $formationService)
    {
        $formationNotif = $formationService->notif(); // Respectez la casse correcte pour les méthodes
        $cours = $coursRepository->findBy([], ['titreCours' => 'DESC']); // Utilisation du repository injecté

        return $this->render('cours/index.html.twig', [
            'cours' => $cours,
            'formationNotif' => $formationNotif,
        ]);
    }
    public function displaySortedByPosteASC(CommentaireRepository $commentaireRepository)
    {
        $commentaires = $this->getDoctrine()->getRepository(Commentaire::class)->findBy([], ['idPoste' => 'ASC']);

        return $this->render('commentaire/index.html.twig', [
            'commentaires' => $commentaires,
        ]);
    }

    
    public function displaySortedByPosteDESC(CommentaireRepository $commentaireRepository)
    {
        $commentaires = $this->getDoctrine()->getRepository(Commentaire::class)->findBy([], ['idPoste' => 'DESC']);

        return $this->render('commentaire/index.html.twig', [
            'commentaires' => $commentaires,
        ]);
    }
}