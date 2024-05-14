<?php

namespace App\Controller;


use App\Entity\Commentaire;
use App\Entity\Poste;


use App\Form\CommentaireType;

use App\Repository\CommentaireRepository;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Validator\Constraints\File;

#[Route('/commentairefront')]
class CommentFrontController extends AbstractController
{
    #[Route('/', name: 'app_commentaire_commentFront', methods: ['GET'])]
    public function index(CommentaireRepository $commentaireRepository): Response
    {
        $postes = $this->getDoctrine()->getRepository(Poste::class)->findAll();
$commentairebyposte = [];

foreach ($postes as $poste) {
    $commentaires = $commentaireRepository->findBy(['idPoste' => $poste->getIdposte()]);
    $commentairebyposte[$poste->getTitre()] = $commentaires;
}

return $this->render('Front/commentFront.html.twig', [
    'commentairebyposte' => $commentairebyposte,
]);

    }
}

