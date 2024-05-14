<?php

namespace App\Controller;

use App\Entity\Formation;
use App\Form\FormationType;
use App\Repository\FormationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Validator\Constraints\File;
use App\Service\FormationService;

#[Route('/formationfront')]
class FormationFrontController extends AbstractController
{
    #[Route('/', name: 'app_formationfront_index', methods: ['GET'])]
    public function index(FormationRepository $formationRepository ,FormationService $formationService): Response
    {
        $formationNotif =$formationService->Notif();
        return $this->render('formationfront/index.html.twig', [
            'formations' => $formationRepository->findAll(),
            'formationNotif' => $formationNotif
        ]);
    }
}
