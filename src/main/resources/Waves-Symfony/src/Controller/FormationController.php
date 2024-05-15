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
use Dompdf\Dompdf;
use Dompdf\Options; 
use App\Service\FormationService;
use Knp\Component\Pager\PaginatorInterface;

#[Route('/formation')]
class FormationController extends AbstractController
{
    #[Route('/', name: 'app_formation_index', methods: ['GET'])]
    public function index(FormationRepository $formationRepository, FormationService $formationService, Request $request, PaginatorInterface $paginator): Response
    {
        $formationNotif = $formationService->Notif();
        $formations = $paginator->paginate(
            $formationRepository->findAll(), // Query to paginate
            $request->query->getInt('page', 1), // Current page number
            2 // Number of items per page
        );
        return $this->render('dashboard/formationdashboard.html.twig', [
            'formations' => $formations, // Pass paginated results to the template
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/new', name: 'app_formation_new', methods: ['GET', 'POST'])]
    public function new(Request $request,FormationService $formationService,FormationRepository $formationRepository, EntityManagerInterface $entityManager): Response
    {
        $formation = new Formation();
        $form = $this->createForm(FormationType::class, $formation);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('affiche')->getData();
            if ($imageFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                try {
                    $imageFile->move(
                        $this->getParameter('images_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                }
                    $formation->setAffiche($newFilename);
            }
    
            $formation->setCreatedAt(new \DateTime());
            $entityManager->persist($formation);
            $entityManager->flush();
            $formationNotif =$formationService->Notif();
            
            return $this->redirectToRoute('app_formation_index', ["formationNotif" => $formationNotif], Response::HTTP_SEE_OTHER);
        }
        $formationNotif =$formationService->Notif();
        return $this->renderForm('dashboard/FormationNewAdmin.html.twig', [
            'formation' => $formation,
            'form' => $form,
            'formationNotif' => $formationNotif
        ]);
    }
    

    #[Route('/{id}', name: 'app_formation_show', methods: ['GET'])]
    public function show(Formation $formation ,FormationService $formationService): Response
    {
        $formationNotif =$formationService->Notif();
        return $this->render('formation/show.html.twig', [
            'formation' => $formation,
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/{id}/edit', name: 'app_formation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request,FormationService $formationService, Formation $formation, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(FormationType::class, $formation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_formation_index', [], Response::HTTP_SEE_OTHER);
        }
        $formationNotif =$formationService->Notif();
        return $this->renderForm('formation/edit.html.twig', [
            'formation' => $formation,
            'form' => $form,
            'formationNotif' => $formationNotif
        ]);
    }

    #[Route('/{id}', name: 'app_formation_delete', methods: ['POST'])]
    public function delete(Request $request, Formation $formation, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$formation->getId(), $request->request->get('_token'))) {
            $entityManager->remove($formation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_formation_index', [], Response::HTTP_SEE_OTHER);
    }


    


 

   


}
