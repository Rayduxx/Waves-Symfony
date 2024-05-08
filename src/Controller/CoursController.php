<?php

namespace App\Controller;

use App\Entity\Cours;
use App\Form\CoursType;
use App\Repository\CoursRepository;
use App\Repository\FormationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Service\FormationService;

#[Route('/cours')]
class CoursController extends AbstractController
{
    #[Route('/', name: 'app_cours_index', methods: ['GET'])]
    public function index(CoursRepository $coursRepository,FormationService $formationService): Response
    {
        $formationNotif =$formationService->Notif();
        return $this->render('cours/index.html.twig', [
            'cours' => $coursRepository->findAll(),
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/new', name: 'app_cours_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,FormationService $formationService): Response
    {
        $cour = new Cours();
        $form = $this->createForm(CoursType::class, $cour);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($cour);
            $entityManager->flush();

            return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
        }

        $formationNotif =$formationService->Notif();

        return $this->renderForm('cours/new.html.twig', [
            'cour' => $cour,
            'form' => $form,
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/{idCours}', name: 'app_cours_show', methods: ['GET'])]
    public function show(Cours $cour,FormationService $formationService): Response
    {
        $formationNotif =$formationService->Notif();
        return $this->render('cours/show.html.twig', [
            'cour' => $cour,
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/{idCours}/edit', name: 'app_cours_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Cours $cour,FormationService $formationService, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CoursType::class, $cour);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
        }

        $formationNotif =$formationService->Notif();

        return $this->renderForm('cours/edit.html.twig', [
            'cour' => $cour,
            'form' => $form,
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/{idCours}', name: 'app_cours_delete', methods: ['POST'])]
    public function delete(Request $request, Cours $cour, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$cour->getIdCours(), $request->request->get('_token'))) {
            $entityManager->remove($cour);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
    }

    
}


