<?php

namespace App\Controller;
use App\Entity\Formation;
use App\Repository\FormationRepository;
use App\Repository\PosteRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class SearchFrontController extends AbstractController
{
    #[Route('/searchfront', name: 'app_formation_searchFront')]
    public function searchFormation(Request $request, FormationRepository $repository): Response
    {
        $query = $request->request->get('query');
        $formations = $repository->searchByNom($query);
        return $this->render('formationfront/search.html.twig', [
            'formations' => $formations
        ]);
    }
    #[Route('/front/search', name: 'app_poste_search')]
    public function searchPoste(Request $request, PosteRepository $repository): Response
    {
        $query = $request->request->get('query');
        $postes = $repository->searchByNom($query);
        return $this->render('Front/search.html.twig', [
            'postes' => $postes
        ]);
    }
}
