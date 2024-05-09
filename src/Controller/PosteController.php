<?php

namespace App\Controller;

use App\Entity\Poste;
use App\Form\PosteType;
use App\Repository\PosteRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Knp\Component\Pager\PaginatorInterface;
use Joli\JoliNotif\Notification;
use Joli\JoliNotif\NotifierFactory;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;



#[Route('/poste')]
class PosteController extends AbstractController
{
    #[Route('/', name: 'app_poste_index', methods: ['GET'])]
    public function index(PosteRepository $posteRepository, Request $request, PaginatorInterface $paginator): Response
    {
        $poste = $paginator->paginate(
            $poste= $posteRepository->findAll(),
            $page= $request->query->getInt('page', 1),
            2
        );
        return $this->render('poste/index.html.twig', [
            'postes' => $poste,
        ]);
    }
     

    #[Route('/new', name: 'app_poste_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, MailerInterface $mailer): Response
    {
        $poste = new Poste();
        $form = $this->createForm(PosteType::class, $poste);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Gérer l'upload de l'image
            $imageFile = $form->get('image')->getData();
    
            // Vérifier si un fichier a été téléchargé
            if ($imageFile) {
                // Générer un nom de fichier unique
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
    
                // Déplacer le fichier vers le répertoire où vous souhaitez le stocker
                try {
                    $imageFile->move(
                        $this->getParameter('images_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // Gérer l'exception si quelque chose ne va pas lors de l'upload du fichier
                }
    
                // Mettre à jour le champ d'image de l'entité avec le nom de fichier
                $poste->setImage($newFilename);
            }

            $audioFile = $form->get('morceau')->getData();
            if ($audioFile) {
                // Générer un nom de fichier unique pour le fichier audio
                $newAudioFilename = uniqid().'.'.$audioFile->guessExtension();
    
                // Déplacer le fichier audio vers le répertoire de destination
                try {
                    $audioFile->move(
                        $this->getParameter('audio_directory'),
                        $newAudioFilename
                    );
                } catch (FileException $e) {
                    // Gérer les erreurs d'upload du fichier audio
                }
    
                // Mettre à jour l'attribut 'morceau' de l'entité Poste avec le nom du fichier audio
                $poste->setMorceau($newAudioFilename);
            }
    
            // Enregistrer l'entité dans la base de données
            $this->sendNotification();
            $entityManager->persist($poste);
            $entityManager->flush();
            $email = (new Email())
            ->from('fatmabha0@gmail.com')
            ->to('azizsalmi330@gmail.com')
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject('Bienvenue chez WAVES')
            ->text('!!')    
            ->html('<p>salut</p>');
            $mailer->send($email);

            return $this->redirectToRoute('app_posteFront_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('poste/new.html.twig', [
            'poste' => $poste,
            'form' => $form,
        ]);
    }

    #[Route('/{idposte}', name: 'app_poste_show', methods: ['GET'])]
    public function show($idposte,PosteRepository $PosteRepository): Response
    {
        return $this->render('poste/show.html.twig', [
            'poste' => $PosteRepository->find($idposte),
        ]);
    }

    #[Route('/{idposte}/edit', name: 'app_poste_edit', methods: ['GET', 'POST'])]
    public function edit($idposte, Request $request, PosteRepository $posteRepository, EntityManagerInterface $entityManager): Response
    {   
        $poste = $posteRepository->find($idposte);
        $form = $this->createForm(PosteType::class, $poste);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_poste_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('poste/edit.html.twig', [
            'poste' => $poste,
            'form' => $form,
        ]);
    }

    #[Route('/delete/{idposte}', name: 'app_poste_delete')]
    public function delete($idposte): Response
    {
        $event = $this->getDoctrine()->getRepository(Poste::class)->find($idposte);
        $em = $this->getDoctrine()->getManager();
        $em->remove($event);
        $em->flush();

        return $this->redirectToRoute('app_poste_index', [], Response::HTTP_SEE_OTHER);
    }
    private function sendNotification(): void
{
    // Create a notifier
    $notifier = NotifierFactory::create();

    // Create a notification
    $notification = (new Notification())
        ->setTitle('Waves : Poste ajouté')
        ->setBody('Poste ajouté avec succès.')
        ->setIcon(__DIR__.'/assets/img/warning.png');

    // Send the notification
    $notifier->send($notification);
}



 
}
