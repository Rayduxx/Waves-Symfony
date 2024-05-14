<?php

namespace App\Controller;
use App\Repository\CoursRepository;
use App\Entity\Cours;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use App\Form\CoursType;

use Doctrine\ORM\EntityManagerInterface;




class SearchController extends AbstractController
{
    #[Route('/search', name: 'app_cours_search')]
    public function searchCours(Request $request, CoursRepository $repository): Response
    {
        $query = $request->request->get('query');
        $cours = $repository->searchByNom($query);
        return $this->render('cours/search.html.twig', [
            'cours' => $cours
        ]);
    }
}
