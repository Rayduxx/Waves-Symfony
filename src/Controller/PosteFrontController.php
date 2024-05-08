<?php

namespace App\Controller;

use App\Entity\Poste;
use App\Entity\Commentaire;

use App\Form\PosteType;
use App\Form\CommentaireType;

use App\Repository\PosteRepository;
use App\Repository\CommentaireRepository;



use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\HttpFoundation\JsonResponse;



#[Route('/posteFront')]
class PosteFrontController extends AbstractController
{
    #[Route('/', name: 'app_posteFront_index', methods: ['GET'])]
    public function index(PosteRepository $posteRepository): Response
    {
        return $this->render('Front/posteFront.html.twig', [
            'postes' => $posteRepository->findAll(),
        ]);
    }


    #[Route('/{idposte}', name: 'app_commentFront_indexC', methods: ['GET'])]
public function show(Request $request, poste $poste, CommentaireRepository $CommentaireRepository): Response
{
    // Récupérer l'ID du sport à partir de la requête
    $posteId = $request->get('idposte');

    // Récupérer le sport correspondant à partir de la base de données
    $poste = $this->getDoctrine()->getRepository(Poste::class)->find($posteId);

    if (!$poste) {
        throw $this->createNotFoundException('Poste non trouvé avec l\'ID : '.$posteId);
    }

    // Récupérer les terrains associés à ce sport
    $commentaires = $CommentaireRepository->findBy(['idPoste' => $poste]);

    // Création d'une nouvelle instance de Commentaire
    $commentaire = new Commentaire();
    
    // Création du formulaire à partir du CommentaireFormType
    $form = $this->createForm(CommentaireType::class, $commentaire);

    // Gestion de la soumission du formulaire
    $form->handleRequest($request);

    // Vérification si le formulaire est soumis et valide
    if ($form->isSubmitted() && $form->isValid()) {
        // Traitement de l'ajout du commentaire à la base de données
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($commentaire);
        $entityManager->flush();

        // Redirection vers la page actuelle pour éviter les soumissions multiples du formulaire
        return $this->redirectToRoute('app_commentaire_commentFront');
    }

    // Rendre le template avec les terrains associés à ce sport
    return $this->render('Front/commentFront.html.twig', [
        'poste' => $poste,
        'commentaires' => $commentaires,
        'form' => $form->createView(),

    ]);

     $idPoste = $request->request->get('idPoste');

        $commentaire = new Commentaire();
        $commentaire->setIdPoste($idPoste);
        $commentaire->setComment($request->request->get('comment'));
        $commentaire->setIduser($request->request->get('iduser'));

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($commentaire);
        $entityManager->flush();

        // Redirection vers la même page pour rafraîchir l'affichage des commentaires
        return $this->redirectToRoute('app_commentFront_indexC', ['idposte' => $idPoste]);

        return $this->renderForm('Front/newC.html.twig', [
            'poste' => $poste,
            'form' => $form,
        ]);
    }





#[Route('/Front/new', name: 'app_commentaireFront_new', methods: ['GET','POST'])]
    public function new(Request $request): Response
    {
        $idPoste = $request->request->get('idPoste');

        $commentaire = new Commentaire();
        $commentaire->getIdPoste($idPoste);
        $commentaire->setComment($request->request->get('comment'));

        $commentaire->setIduser($request->request->get('iduser'));

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($commentaire);
        $entityManager->flush();

        // Redirection vers la même page pour rafraîchir l'affichage des commentaires
        return $this->redirectToRoute('app_commentFront_indexC', ['idposte' => $idPoste]);
    }
   




}

  
    

  
